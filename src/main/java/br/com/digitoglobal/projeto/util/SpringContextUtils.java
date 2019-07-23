package br.com.digitoglobal.projeto.util;

import java.io.IOException;
import java.util.Arrays;

import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.jsf.FacesContextUtils;

import configuracao.spring.ApplicationContextProvider;
import configuracao.spring.SpringConfiguration;

public abstract class SpringContextUtils {

	public static final Class<?>[] contextAnnotationClasses;

	public static final SpringContextLoadType DEFAULT_CONTEXT_LOAD_TYPE;

	// ---- Configurações que devem ser verificadas e alteradas de acordo com a aplicação em questão ----
	public static final String[] contextsXMLPath = new String[]{"classpath:applicationContext.xml"};
	static {
		contextAnnotationClasses = new Class<?>[]{SpringConfiguration.class}; // Aqui vai as classes que estão anotadas com @Configuration
		DEFAULT_CONTEXT_LOAD_TYPE = SpringContextLoadType.CONFIGURATION_ANNOTATION;
	}
	// ---- ****** ----

	public enum SpringContextLoadType {CONFIGURATION_ANNOTATION, XML, BOTH}

    private static ApplicationContext context;

	// Construtor "private" para impedir instanciação desta classe mesmo internamente.
	private SpringContextUtils() {}

	public static <T> T getBean(String name) {
		return (T) getContext(null, getActiveProfiles()).getBean(name);
	}

	public static <T> T getBeanWithConstructorArgs(String name, Object... constructorArgs) {
		return (T) getContext(null, getActiveProfiles()).getBean(name, constructorArgs);
	}

	public static <T> T getBean(String name, String... activeProfiles) {
		return getConfigurationAnnotationBean(name, activeProfiles);
	}

	public static <T> T getBeanWithConstructorArgs(String name, Object[] constructorArgs, String... activeProfiles) {
		return (T) getContext(SpringContextLoadType.CONFIGURATION_ANNOTATION, activeProfiles).getBean(name, constructorArgs);
	}

	public static <T> T getBean(Class<?> clazz) {
		return (T) getContext(null, getActiveProfiles()).getBean(clazz);
	}

	public static <T> T getBean(Class<?> clazz, String... activeProfiles) {
		return getConfigurationAnnotationBean(clazz, activeProfiles);
	}

	public static <T> T getXMLBean(String name) {
		return (T) getContext(SpringContextLoadType.XML, getActiveProfiles()).getBean(name);
	}

	public static <T> T getXMLBean(Class<?> clazz) {
		return (T) getContext(SpringContextLoadType.XML, getActiveProfiles()).getBean(clazz);
	}

	public static <T> T getConfigurationAnnotationBean(String name) {
		return (T) getContext(SpringContextLoadType.CONFIGURATION_ANNOTATION, getActiveProfiles()).getBean(name);
	}

	public static <T> T getConfigurationAnnotationBean(Class<?> clazz) {
		return (T) getContext(SpringContextLoadType.CONFIGURATION_ANNOTATION, getActiveProfiles()).getBean(clazz);
	}

	public static <T> T getConfigurationAnnotationBean(String name, String... activeProfiles) {
		return (T) getContext(SpringContextLoadType.CONFIGURATION_ANNOTATION, activeProfiles).getBean(name);
	}

	public static <T> T getConfigurationAnnotationBean(Class<?> clazz, String... activeProfiles) {
		return (T) getContext(SpringContextLoadType.CONFIGURATION_ANNOTATION, activeProfiles).getBean(clazz);
	}

	public static void changeProfiles(String... activeProfiles) {
		if (context != null) {
			if (activeProfiles != null && activeProfiles.length != 0) {
				if (context instanceof AnnotationConfigApplicationContext) {
					((AnnotationConfigApplicationContext)context).getEnvironment().setActiveProfiles(activeProfiles);
					((AnnotationConfigApplicationContext) context).refresh();
				}
			}
		}
	}

	public static Resource[] findResources(String locationPattern) throws IOException {
		return new PathMatchingResourcePatternResolver().getResources(locationPattern);
	}

	public static void init(final ApplicationContext contextApp) {
		context = contextApp;
	}

	public static String[] getActiveProfiles() {
		if (context == null) return null;
		else return context.getEnvironment().getActiveProfiles();
	}

	public synchronized static ApplicationContext getContext() {
		return getContext(null, getActiveProfiles());
	}

	private synchronized static ApplicationContext getContext(SpringContextLoadType springContextLoadType, String... activeProfiles) {
		if (context == null) {
			if (FacesContext.getCurrentInstance() != null) {
				context = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			}

			if (context == null) {
				context = ApplicationContextProvider.getApplicationContext();
			}

			if (context == null) {
				if (springContextLoadType == null) springContextLoadType = DEFAULT_CONTEXT_LOAD_TYPE;
				switch (springContextLoadType) {
					case CONFIGURATION_ANNOTATION: {
						context = getAnnotationConfigContext(null, activeProfiles);
					} break;
					case XML: {
						context = new FileSystemXmlApplicationContext(contextsXMLPath);
					} break;
					case BOTH: {
						context = new FileSystemXmlApplicationContext(contextsXMLPath);
						context = getAnnotationConfigContext(context, activeProfiles);
					} break;
					default: throw new RuntimeException("Tipo de contexto de carregamento de beans do spring incorreto. Tipo: "+springContextLoadType);
				}
			}
		} else {
			if (springContextLoadType == SpringContextLoadType.CONFIGURATION_ANNOTATION) {
				// Se os profiles forem diferentes, deve-se atualizar os profiles.
				if (activeProfiles != null && activeProfiles.length != 0 && !Arrays.equals(activeProfiles, context.getEnvironment().getActiveProfiles())) {
					changeProfiles(activeProfiles);
				}
			}
		}
		return context;
	}

	private static ApplicationContext getAnnotationConfigContext(ApplicationContext parentContext, String... activeProfiles) {
		AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext();
		if (parentContext != null) annotationContext.setParent(parentContext);
		if (activeProfiles != null) annotationContext.getEnvironment().setActiveProfiles(activeProfiles);
		annotationContext.register(contextAnnotationClasses);
		annotationContext.refresh();
		return annotationContext;
	}

}