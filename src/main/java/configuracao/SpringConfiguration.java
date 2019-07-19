package configuracao;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlProcessor.ResolutionMethod;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@ComponentScan(basePackages = {"configuracao.*", "br.com.*"})
public class SpringConfiguration {

	@Value("${spring.profiles.active}")
	private String valor;
	
//	@Autowired
//	private Environment environment;
//	
//	@Autowired
//	private ApplicationContext applicationContext;
//	
	public SpringConfiguration() {
		System.out.println("Entrou no SpringConfiguration");
	}
	
	//To resolve ${} in @Value
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
	    Resource yamlResource = new ClassPathResource("application.yml");
	    yaml.setResources(yamlResource);
	    yaml.setResolutionMethod(ResolutionMethod.FIRST_FOUND);
	    Properties properties = yaml.getObject();
	    
		PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
		
//		try {
//	        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
//	        factory.setResources(new ClassPathResource("config/application.yml"));
//	        return factory.getObject();
//	    } catch (Exception e) {
//	        log.error("Failed to read application.yml to get default profile");
//	        return null;
//	    }
		
////        ppc.setLocations new FileSystemResource("/etc/webapp_properties/security-token.properties"),
////                new ClassPathResource("config/WebApp.properties"),
////                new ClassPathResource("config/" + System.getenv("CURRENTENV") + "/WebApp.properties"));
//		ppc.setLocation(new ClassPathResource("-desenvolvimento.yml"));
//        ppc.setPlaceholderPrefix("application");
        ppc.setProperties(properties);
        return ppc;
	}
	
	@Bean
	public static YamlPropertiesFactoryBean yamlPropertiesFactoryBean() {
	    YamlPropertiesFactoryBean propertiesFactoryBean = new YamlPropertiesFactoryBean();
	    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	    try {
	        Resource[] resources = resolver.getResources("/**/*.yml");
	        propertiesFactoryBean.setResources(resources);
	    } catch (IOException e) {
	        throw new IllegalStateException(e);
	    }
	    return propertiesFactoryBean;
	}
	
}
