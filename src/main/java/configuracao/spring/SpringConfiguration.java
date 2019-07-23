package configuracao.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import configuracao.YamlFileApplicationContextInitializer;
import configuracao.YamlPropertyLoaderFactory;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackages = {"configuracao.*", "br.com.*"}) 
@PropertySource(value = "classpath:application.yml", factory = YamlPropertyLoaderFactory.class)
public class SpringConfiguration {
	
	public static void main(String[] args) {
		
		AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
		springContext.register(SpringConfiguration.class);
		springContext.refresh();
		
		System.out.println(springContext);
		
		
	}
	
	@Autowired Environment environment;
	
	public SpringConfiguration() {
		System.out.println("Entrou no SpringConfiguration");
	}
	
	@Bean
	public YamlFileApplicationContextInitializer teste() {
		return new YamlFileApplicationContextInitializer();
	}
	
	//To resolve ${} in @Value
	@Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
//        pspc.setProperties(properties);
        pspc.setIgnoreUnresolvablePlaceholders(true);
        return pspc;
    }
	
	//To resolve ${} in @Value
//	@Bean
//	public static PropertySourcesPlaceholderConfigurer properties() {
//		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//	    Resource yamlResource = new ClassPathResource("application.yml");
//	    yaml.setResources(yamlResource);
//	    yaml.setResolutionMethod(ResolutionMethod.FIRST_FOUND);
//	    Properties properties = yaml.getObject();
//	    
//	    // convert to stream
//	    InputStream stream = null;
//		try(ByteArrayOutputStream output = new ByteArrayOutputStream()) {
//			properties.store(output, null);
//			stream = new ByteArrayInputStream(output.toByteArray());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Resource [] resources = new Resource[] {new InputStreamResource(stream)};
//	    
//		PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
//		ppc.setLocations(resources);
//		ppc.setProperties(properties);
//		ppc.setIgnoreUnresolvablePlaceholders(true);
//		
//		MutablePropertySources pro = (MutablePropertySources) ppc.getAppliedPropertySources();
//		PropertySource ps = new PropertiesPropertySource("applicationProperties", properties);
//		pro.addLast(ps);
//		
//		try {
//	        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
//	        factory.setResources(new ClassPathResource("config/application.yml"));
//	        return factory.getObject();
//	    } catch (Exception e) {
//	        log.error("Failed to read application.yml to get default profile");
//	        return null;
//	    }
//		
////        ppc.setLocations new FileSystemResource("/etc/webapp_properties/security-token.properties"),
////                new ClassPathResource("config/WebApp.properties"),
////                new ClassPathResource("config/" + System.getenv("CURRENTENV") + "/WebApp.properties"));
//		ppc.setLocation(new ClassPathResource("-desenvolvimento.yml"));
//        ppc.setPlaceholderPrefix("application");
//		
//        
//        return ppc;
//	}
	
//	@Bean
//	public static YamlPropertiesFactoryBean yamlPropertiesFactoryBean() {
//	    YamlPropertiesFactoryBean propertiesFactoryBean = new YamlPropertiesFactoryBean();
//	    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//	    try {
//	        Resource[] resources = resolver.getResources("/**/*.yml");
//	        propertiesFactoryBean.setResources(resources);
//	    } catch (IOException e) {
//	        throw new IllegalStateException(e);
//	    }
//	    return propertiesFactoryBean;
//	}
	
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(5);
		threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
		return threadPoolTaskScheduler;
	}
	
	@Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource bundle = new ResourceBundleMessageSource();
        bundle.setBasename("messages");
        return bundle;
    }
	
	@Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        Map<String, Object> map = new HashMap<>();
        map.put("view", new SpringViewScope());
        customScopeConfigurer.setScopes(map);
        return customScopeConfigurer;
    }
	
	@Bean
    @Lazy(false)
    public ApplicationContextProvider applicationContextProvider() {
        return new ApplicationContextProvider();
    }
	
	@Bean
    @Lazy(false)
	@DependsOn("applicationContextProvider")
    public String applicationInitLog() {
		System.out.println("Environment: "+environment);
		System.out.println("PROFILE => "+environment.getProperty("spring.profile.active"));
        return null;
    }
	
}
