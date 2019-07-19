package configuracao.servlet.container.initializer;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public interface ApplicationServletInitializer  {

	void onStartup(ServletContext servletContext, Map<String,Object> sharedInitializerObjects) throws ServletException;
	Integer getOrder();
	
}
