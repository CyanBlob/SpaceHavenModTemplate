package com.cyanblob.SpaceHavenMod;

import fi.bugbyte.spacehaven.gui.*;
import fi.bugbyte.spacehaven.stuff.Entity;
import fi.bugbyte.spacehaven.world.Ship;

import java.util.Comparator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

class newComp implements Comparator {

   newComp() {}
 
   public int compare(MenuSystemItems.MultiSelectedUnitBox o1, MenuSystemItems.MultiSelectedUnitBox o2) {
      if (o1.getEntity().getAi() == null) {
         return 0;
      } else if (o2.getEntity().getAi() == null) {
         return 0;
      } else {
         Ship h1 = o1.getEntity().getAi().getHomeShip();
         Ship h2 = o2.getEntity().getAi().getHomeShip();
         if (h1 != h2 && h1 != null && h2 != null) {
            return h1.getShipId() > h2.getShipId() ? 1 : -1;
         } else {
        	 //MODDED (TASK)
        	 return Entity.taskToString(o1.getEntity().getCurrentTask().task).compareTo(Entity.taskToString(o2.getEntity().getCurrentTask().task));
             // MODDED (ID):
        	 //return o1.getEntity().getEntityId() - o2.getEntity().getEntityId();
        	 // ORIGININAL:
             //return o1.getEntity().getName().compareTo(o2.getEntity().getName());

         }
      }
   }
   
   public int compare(Object o1, Object o2) {
	   return 1;
   }
}


@Aspect
public class guiAspect {

    @Pointcut("set(java.util.Comparator fi.bugbyte.spacehaven.gui.GUIHelper.TotalCrew.comp) && args(comp) && within(fi.bugbyte..*)")
    public void modComp(Comparator comp) {
    }

    @Around("modComp(comp)")
    public Object pModComp(ProceedingJoinPoint pjp, Comparator comp) throws Throwable {
    	System.out.println("MODDING COMP");
    	return pjp.proceed(new Object[] {new newComp()});
    }
}