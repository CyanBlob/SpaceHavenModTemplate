rem copy to C:\Program Files (x86)\Steam\steamapps\common\SpaceHaven before running
java --add-opens java.base/java.lang=ALL-UNNAMED -javaagent:.\aspectjweaver-1.9.19.jar -classpath "code;mods/*;aspectj-1.9.19.jar;spacehaven.jar" fi.bugbyte.spacehaven.steam.SpacehavenSteam