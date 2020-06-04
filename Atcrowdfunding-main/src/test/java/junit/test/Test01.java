package junit.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

public class Test01 {

	ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");
	ProcessEngine processEngine = (ProcessEngine)ioc.getBean("processEngine");

	//2.部署流程定义
		@Test
		public void test02(){
			
			
			RepositoryService repositoryService = processEngine.getRepositoryService();
			
			Deployment deploy = repositoryService.createDeployment().addClasspathResource("MyProcess1.bpmn").deploy();
			
			System.out.println("deploy="+deploy);
		}
}
