package configuracao.servlet.application.initializers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.webapp.filter.FileUploadFilter;

import configuracao.servlet.container.initializer.ApplicationServletInitializer;

public class GenericServletInitializer implements ApplicationServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext, Map<String, Object> sharedInitializerObjects) throws ServletException {
		
		// Configurando a "welcome page".
		String welcomePage = "/index.jsf";
		if (welcomePage != null && !welcomePage.trim().isEmpty()) {
			FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("welcomePageFilter", new Filter() {
				@Override public void init(FilterConfig filterConfig) throws ServletException {}
				@Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
					String path = ((HttpServletRequest) servletRequest).getServletPath();
					if (path != null && !path.trim().isEmpty()) {
						if (path.equalsIgnoreCase("/")) {
							RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher(welcomePage);
							requestDispatcher.forward(servletRequest, servletResponse);
							return;
						}
					}
					filterChain.doFilter(servletRequest, servletResponse);
				}
				@Override public void destroy() {}
			});
			filterRegistration.addMappingForUrlPatterns(null, false, "/");
		}
		
		FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("CharacterEncodingFilter", FileUploadFilter.class);
        filterRegistration.setInitParameter("encoding", "UTF-8");
        filterRegistration.setInitParameter("forceEncoding", "true");
        filterRegistration.addMappingForUrlPatterns(null, false, "/*");
		
	}

	@Override
	public Integer getOrder() {
		return 3;
	}

}
