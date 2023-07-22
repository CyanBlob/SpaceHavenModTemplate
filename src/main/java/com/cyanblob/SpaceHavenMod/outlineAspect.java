package com.cyanblob.SpaceHavenMod;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class outlineAspect {
    @Pointcut("call(boolean fi.bugbyte.spacehaven.stuff.Character.shouldDrawOutline())")
    public void shouldDrawOutline() {
    }

    @Around("shouldDrawOutline()")
    public Object showOutlines(ProceedingJoinPoint joinPoint) throws Throwable {
        return true;
    }
}