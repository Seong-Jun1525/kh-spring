package com.kh.spring.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 스프링 어플리케이션은 대부분 MVC 패턴에 따라
 * 
 * 		Web Layer -> Business Layer -> Data Layer로 구분할 수있다.
 * 
 * * WebLayer : REST API를 제공하고 클라이언트 중심의 로직을 적용
 * 
 * * Business Layer : 내부 정책에 따라 로직을 개발
 * 							=> 서비스의 핵심들이 주로 비즈니스 달라짐
 * 								(* 개발자들이 집중해야 하는 부분)
 * 
 * * Data Layer : 데이터 베이스 및 외부 서비스와의 연동
 * ---------------------------------------------------------------------
 * AOP (Aspect Oriented Programming, 관점 지향 프로그래밍)
 * 
 * AOP를 통해서 위의 각 레이어에서 반복되는 공통 관심 사항들을 분리하여 관리함으로서
 * 코드의 재사용성을 높이고, 유지보수성을 강화할 수 있다.
 * 
 * ex) 로깅, 트랜잭션 처리(관리), 보안, ...
 * 
 * [장점]
 * 	- 특정 메서드만 선택적으로 로깅할 수 있다(로그를 남길 수 있다)
 * 	- 재사용성이 높아진다
 * 
 * [단점]
 * 	- AOP 설정 작업 다소 복잡함
 * 
 * * 용어
 * 		- Aspect : Advice + PointCut. 여러 객체에 공통으로 적용되는 기능을 분리한 클래스
 * 		- JoinPoint : 특정 작업이 시작되는 시점
 * 		- PointCut : 실제 Advice가 적용되는 부분
 * 		- Advice : 시점에 따라 삽입되어 동작될 코드(공통되는 부분, 부가기능)
 * 		- Weaving : 해당 시점에 공통 부분(코드)를 끼워 넣는 작업
 * 
 * * AOP 적용
 * 		- @Aspect : 해당 클래스가 Apsect라는 것을 선언 (AOP를 적용할 클래스)
 * 		- @PointCut : Advice를 적용할 JoinPoint 정의 시 사용
 * 						해당 AOP를 실행할 특정 메서드를 지정(대상)
 * 		- 시점 (Advice 실행 시점)
 * 			+ @Before : 대상 메서드 실행 전 Advice(기능) 실행 됨
 * 			+ @After : 대상 메서드 실행 후 Advice 실행 됨
 * 				+ @AfterReturning : 대상 메서드가 정상적으로 동작된 후 Advice 실행
 * 				+ @AfterThrowing : 대상 메서드가 비정상적으로 동작된 후 (예외 반환) Advice 실행
 * 			+ @Around : 대상 메서드를 감싸서 호출 전/후로 Advice 실행
 */

@Slf4j // lombok에서 제공해주는 Logger 객체. 변수명 : log
@Aspect
@Component
public class LoggingAspect {
	
	// 대상 지정
	@Pointcut("execution(* com.kh.spring..controller.*.*(..))") // 실행되는 메서드 지정, 접근 제한자 지정할 수도 있음
	private void controllerPointCut() { }
	
	// 부가기능 정의(Advice)
	@Before("controllerPointCut()") // PointCut을 지정한 메서드 작성
	public void beforeAdvice(JoinPoint joinPoint) {
		// 실행되는 메서드 정보를 추출
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		
		// 파라미터 추출
		Object[] obj = joinPoint.getArgs();
		
		log.info("=============== Before ===============");
		
		log.info("method info :: {}", methodSignature);
		log.info("method name :: {}", method);
		log.info("parameter :: {}", Arrays.toString(obj));
	}
	
	@AfterReturning(value = "controllerPointCut()", returning = "obj")
	public void afterRetruningAdvoice(JoinPoint joinPoint, Object obj) {
		log.info("=============== After Returning Advice ===============");
		log.info("object :: {}", obj);
	}

	@Around("controllerPointCut()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable { // JoinPoint 객체를 좀 더 세분화해서 전달받음
		log.info("=============== Around Advice ===============");
		long start = System.currentTimeMillis();
		
		Object execute = joinPoint.proceed();
		
		long end = System.currentTimeMillis();
		
		log.info("info :: {}", joinPoint.getSignature());
		log.info("execution time :: {}", end - start);
		
		return execute;
	}
	
	@AfterThrowing(pointcut = "controllerPointCut()", throwing="error")
	public void thrwoingAdvice(JoinPoint joinPoint, Throwable error) {
		log.info("=============== After Throwing ===============");
		log.info("method :: {}", joinPoint.getSignature()); // 메서드 정보
		log.info("error :: {}", error.getMessage());
	}
}
