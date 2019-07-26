package configuracao.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import configuracao.ApplicationEnvironment;
import configuracao.YamlPropertyLoaderFactory;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackages = {"configuracao.*", "br.com.*"}) 
@PropertySource(value = "classpath:"+ApplicationEnvironment.PROPERTY_SOURCE_DEFAULT_NAME
			 	,name = "applicationProperties"
				,factory = YamlPropertyLoaderFactory.class)
public class SpringConfiguration {
	
	private final Logger logger;
	
	@Autowired Environment environment;
	
	public SpringConfiguration() {
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	//To resolve ${} in @Value
	@Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        pspc.setIgnoreUnresolvablePlaceholders(true);
        return pspc;
    }
	
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
		logger.info("PROFILE => "+environment.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME));
        return null;
    }
	
}
