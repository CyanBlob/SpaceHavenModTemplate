package com.cyanblob.SpaceHavenMod;

import fi.bugbyte.framework.screen.TextIconTooltip;
import fi.bugbyte.spacehaven.gui.GUIHelper.PlayerShipListItem;
import fi.bugbyte.spacehaven.world.Ship;

import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class storageGuiAspect {

    int storageUsed = 0;
    int storageCapacity = 0;

    //@Pointcut("execution(fi.bugbyte.spacehaven.gui.GUIHelper$PlayerShipListItem.new(..)) && within(fi.bugbyte.spacehaven.gui..*)")
    //public void postConstruct() {
    //}

    //@After("postConstruct()")
    //public void construct(JoinPoint joinPoint) throws Throwable {
    //};

    @Pointcut("execution(void fi.bugbyte.spacehaven.gui.GUIHelper$PlayerShipListItem.updateMass()) && within(fi.bugbyte..*)")
    public void postUpdateMass() {
    }

    @After("postUpdateMass()")
    public void updateGui(JoinPoint joinPoint) throws Throwable {
        PlayerShipListItem _this = (PlayerShipListItem) joinPoint.getThis();
        
        // Use reflection to access private field "ship"
        Field privateUriField = _this.getClass().getDeclaredField("ship");
        privateUriField.setAccessible(true);
        Ship ship = (Ship) privateUriField.get(_this);

        // Use reflection to access private field "mass"
        privateUriField = _this.getClass().getDeclaredField("mass");
        privateUriField.setAccessible(true);
        TextIconTooltip mass = (TextIconTooltip) privateUriField.get(_this);
        
        storageUsed = 0;
        storageCapacity = 0;
        
        ship.getStorage().forEach(storage -> {
            storageUsed += storage.getInventory().getInStorageSize() + storage.getInventory().getReservedInSize();
            storageCapacity += storage.capacity;
        });
        
        mass.setExtraToolTip(mass.getExtraToolTip() + "\nStorage: " + storageUsed + " / " + storageCapacity);
    };
}