package com.kh.test.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	// 롬복 없이 로그 객체 사용. Logger 객체 필요
	private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);
	
	// 기본 패키지 경로 내의 모든 메서드를 대상
	@Pointcut("execution(* com.kh.test..*.*(..))") // controller, service 등은 .. 이렇게 생략하고 그 안에는 .*. 이렇게 작성한다
	private void allPointCut() {}
	
	// 호출 전 메서드명, 파라미터 정보를 로그로 출력
	@Before("allPointCut()")
	public void beforeAdvice(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		
		Object[] obj = joinPoint.getArgs();

		log.info("=============== Before ===============");
		
		log.info("method info :: {}", joinPoint.getSignature().getName());
		log.info("parameter :: {}", joinPoint.getArgs());
	}
	
	@Around("allPointCut()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("=============== Around Advice ===============");
		
		long start = System.currentTimeMillis();
		
		Object execute = joinPoint.proceed();
		
		long end = System.currentTimeMillis();

		log.info("info :: {}", joinPoint.getSignature());
		log.info("execution time :: {}", (end - start));
		
		return execute;
	}
}
