package configuracao.servlet.container.initializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

@HandlesTypes(ApplicationServletInitializer.class)
public class ApplicationServletContainerInitializer implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> servletInitializerClasses, ServletContext servletContext) throws ServletException {
		List<ApplicationServletInitializer> initializers = new LinkedList<>();

		if (servletInitializerClasses != null) {
			for (Class<?> initializer : servletInitializerClasses) {
				// Be defensive: Some servlet containers provide us with invalid classes,
				// no matter what @HandlesTypes says...
				if (!initializer.isInterface() && !Modifier.isAbstract(initializer.getModifiers()) &&
						ApplicationServletInitializer.class.isAssignableFrom(initializer)) {
					try {
						Constructor<?> ctor = initializer.getDeclaredConstructor();
						if ((!Modifier.isPublic(ctor.getModifiers()) ||
								!Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
							ctor.setAccessible(true);
						}
						
						initializers.add((ApplicationServletInitializer) ctor.newInstance());
					}
					catch (Throwable ex) {
						throw new ServletException("Falha ao instanciar classe do tipo ApplicationServletInitializer", ex);
					}
				}
			}
		}

		if (initializers.isEmpty()) {
			servletContext.log("Nenhuma classe do tipo \"ApplicationServletInitializer\" (inicializadoras do contexto de servlets) detectada no classpath.");
			return;
		}

		servletContext.log(initializers.size() + " Classes inicializadores do contexto de servlets encontradas no classpath (instancias do tipo \"ApplicationServletInitializer\")");
		
		// Ordenando.
		initializers.sort(Comparator.comparing(i -> ((ApplicationServletInitializer)i).getOrder()));
		
		// Criando mapa de objetos compartilhado.
		Map<String,Object> sharedInitializerObjects = new HashMap<String,Object>();
		
		// Executando initializers.
		for (ApplicationServletInitializer initializer : initializers) {
			initializer.onStartup(servletContext, sharedInitializerObjects);
		}
		sharedInitializerObjects.clear();
	}
	
}
