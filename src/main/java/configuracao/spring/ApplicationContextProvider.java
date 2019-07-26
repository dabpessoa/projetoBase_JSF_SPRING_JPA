package configuracao.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;

public class ApplicationContextProvider implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	public static <T> T getBean(Class<T> clazz){
		return applicationContext.getBean(clazz);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		if (ctx.getEnvironment().getActiveProfiles() == null || ctx.getEnvironment().getActiveProfiles().length == 0) {
			((ConfigurableEnvironment)ctx.getEnvironment()).setActiveProfiles(ctx.getEnvironment().getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME));
		}
		ApplicationContextProvider.applicationContext = ctx;
	}
	
	public void destroy(){
		applicationContext = null;
	}
	
	public ApplicationContext getContext() {
		return applicationContext;
	}

}
