# SpaceHavenModTemplate
A template for creating Space Haven mods by patching game logic at runtime

## Developing a mod
### Install/update Java
Java 17+ is currently required. Any implementation is fine, but I used this one: https://adoptium.net/temurin/releases/  
This must be the first Java environment found, so you can set the JAVA_HOME environment variable to point to the "home" folder inside of the Java installation directory  

### Install Maven
This is the build tool you will use to build your mods. You can install it from a package manager (homebrew for MacOS, scoop for Windows, etc.) or you can follow the instructions here: https://maven.apache.org/install.html  
You can verify that it was installed properly by opening a terminal/PowerShell window and running `mvn --version`. The output should look similar to:
```shell
Apache Maven 3.9.3 (21122926829f1ead511c958d89bd2f672198ae9f)
Maven home: /opt/homebrew/Cellar/maven/3.9.3/libexec
Java version: 17.0.8, vendor: Eclipse Adoptium, runtime: /Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "14.0", arch: "x86_64", family: "mac"
```

### Clone the repo
`git clone https://github.com/CyanBlob/SpaceHavenModTemplate`  

### Add Space Haven as a dependency
This is required for AspectJ and Maven to locate the game's classes. I cannot add the game's .jar file to this repo for obvious reasons.  
First, navigate into the repo with `cd /path/to/SpaceHavenModTemplate`. Then, add the file with  
`mvn install:install-file -DgroupId="fi.bugbyte" -DartifactId=spacehaven -Dpackaging=jar -Dversion=1.0.0 -Dfile="</path/to/spacehaven.jar>" -DgeneratePom=true`  

Make sure to use the correct file path in the `-Dfile` argument above.  
Note: On MacOS, the game is shipped as a `.app` file. Don't panic, just give the path as `</path/to/spacehaven.app/Contents/Resources/spacehaven.jar`  

### Creating a mod
This project uses AspectJ to provide code injection when the game loads. There are some simple example mods in `src/main/java/com/cyanblob/SpaceHavenMod`. Aside from those, here are some useful links:  
https://eclipse.dev/aspectj/doc/released/devguide/ltw-configuration.html  
https://eclipse.dev/aspectj/doc/released/adk15notebook/ataspectj-pcadvice.html  

The general idea is that we define locations where we want code to be inject ("pointcuts") and can then run code before, after, or around that location. The "around" pointcut is the most powerful because you can decide whether or not to run the original code via the `proceed` function. See `guiAspect.java` as an example of this  

Note that AspectJ provides two modes: an annotation mode that uses annotations in `.java` files and another style that uses `.aj` files with custom syntax. I was only able to get load time weaving (LTW) working with the annotations, but it may be possible to use the `.aj` files as well

### Building the mod
Building is simple. From within the SpaceHavenModTemplate directory, run: `mvn package`  
If all goes well, you will have a `.jar` file created in the `target` directory. This is the mod file.

## Adding mods + running the game
Navigate to the directory containing `spacehaven.jar`. You can open this directory by right-clicking the game in your Steam library, then clicking `Manage -> Browse Local Game Files`  
Note: On MacOS this will take you to a directory containing `spacehaven.app` instead of `spacehaven.jar`. Just right-click the .app file and select "Show Package Contents". Then go into "Contents/Resources" to find the `.jar` file  
  
Inside the directory containing the `.jar` file, create a folder named "mods". This is where you will place your mods.  
  
Then, download `aspectj-1.9.19.jar` and `aspectjweaver-1.9.19` from [here](https://github.com/eclipse-aspectj/aspectj/releases) and [here](https://repo1.maven.org/maven2/org/aspectj/aspectjweaver/1.9.19/), respectively. Place these files in the same folder as `spacehaven.jar` (_NOT_ the mods folder)

Then, copy the `config.json` file from `SpaceHavenModTemplate/copy_to_game_dir` into the same folder as `spacehaven.jar`. You will have to overwrite the existing file  
  
Lastly, edit `config.json` to add an entry for every mod you wish to use in the `classPath` section. It should include the "mod" part of the path, i.e. `"mods/my_really_cool_mod.jar"`. Make sure to add a comma at the end of each entry in the `classPath` section aside from the last entry in the list

You can now run the game as normal directly from Steam. Enjoy!