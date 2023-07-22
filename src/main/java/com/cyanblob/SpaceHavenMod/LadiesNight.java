package com.cyanblob.SpaceHavenMod;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LadiesNight {
    @Pointcut("call(boolean fi.bugbyte.spacehaven.stuff.Character.isFemale())")
    public void isFemale() {
    }

    @Around("isFemale()")
    public Object toLowerCase(ProceedingJoinPoint joinPoint) throws Throwable {
        return true;
    }
}