package junitActiviti;

import org.activiti.engine.ProcessEngine;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestActiviti {
		
	ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");
	@Test
	public void test01() {
		ProcessEngine processEgine  =  (ProcessEngine) ioc.getBean("processEngine");
		
		System.out.println("processEgine: "+processEgine);
	}
}
