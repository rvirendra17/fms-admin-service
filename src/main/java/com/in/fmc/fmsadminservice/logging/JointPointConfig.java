package com.in.fmc.fmsadminservice.logging;

import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JointPointConfig {

	@Pointcut("execution(* com.in.fmc.fmsadminservice.controllers.*.*(..))"
			+ "||execution(* com.in.fmc.fmsadminservice.services.*.*(..))"
			+ "||execution(* com.in.fmc.fmsadminservice.services.impl*.*(..))"
			+ "||execution(* com.in.fmc.fmsadminservice.exceptionhandlers.*.*(..))"
			+ "||execution(* com.in.fmc.fmsadminservice.utils.*.*(..))")
	public void methodExecution() {
		log.trace("methodExecution");
	}

	@Pointcut("execution(* com.in.fmc.fmsadminservice.controllers.*.*(..))"
			+ "||execution(* com.in.fmc.fmsadminservice.services.*.*(..))"
			+ "||execution(* com.in.fmc.fmsadminservice.services.impl*.*(..))"
			+ "||execution(* com.in.fmc.fmsadminservice.exceptionhandlers.*.*(..))"
			+ "||execution(* com.in.fmc.fmsadminservice.utils.*.*(..))")
	public void methodExecutionReturn() {
		log.trace("methodExecutionReturn");
	}
}
