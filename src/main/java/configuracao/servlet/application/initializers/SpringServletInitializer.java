package configuracao.servlet.application.initializers;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import configuracao.ApplicationEnvironment;
import configuracao.servlet.container.initializer.ApplicationServletInitializer;

public class SpringServletInitializer implements ApplicationServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext, Map<String,Object> sharedInitializerObjects) throws ServletException {

		// Inicializando Spring
		AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
		springContext.register(getRootConfigClasses());
		servletContext.addListener(new ContextLoaderListener(springContext));
		
		// Configurando o SpringMVC (para serviços REST)
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher", new DispatcherServlet(springContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/api/*");
        // FIM - Configurando o SpringMVC
		
	}
	
	public Class<?>[] getRootConfigClasses() {
		return ApplicationEnvironment.springConfigurationClasses;
	}
	
	@Override
	public Integer getOrder() {
		return 1;
	}
	
}
