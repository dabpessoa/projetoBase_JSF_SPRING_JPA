package configuracao.servlet.application.initializers;

import java.util.Map;

import javax.faces.webapp.FacesServlet;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.primefaces.webapp.filter.FileUploadFilter;

import configuracao.servlet.container.initializer.ApplicationServletInitializer;

public class JSFServletInitializer implements ApplicationServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext, Map<String,Object> sharedInitializerObjects) throws ServletException {
		
		// Adicionando o servlet do JSF
		ServletRegistration.Dynamic facesServlet = servletContext.addServlet("Faces Servlet", new FacesServlet());
		facesServlet.addMapping("*.xhtml", "*.jsf");
		facesServlet.setLoadOnStartup(1);
		
		FilterRegistration.Dynamic fileUploadFilter = servletContext.addFilter("PrimeFaces FileUpload Filter", FileUploadFilter.class);
        fileUploadFilter.setInitParameter("thresholdSize", "51200");
        fileUploadFilter.addMappingForServletNames(null, false, "Faces Servlet");
		
		servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE" , "true" );
		servletContext.setInitParameter("javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL" , "true" );
		servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
		servletContext.setInitParameter("com.sun.faces.allowTextChildren" , "true" );
		servletContext.setInitParameter("com.sun.faces.enableMissingResourceLibraryDetection" , "false" );
		servletContext.setInitParameter("primefaces.THEME" , "bootstrap");
		servletContext.setInitParameter("primefaces.UPLOADER" , "native" );
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", "true");
		
	}
	
	@Override
	public Integer getOrder() {
		return 2;
	}

}
