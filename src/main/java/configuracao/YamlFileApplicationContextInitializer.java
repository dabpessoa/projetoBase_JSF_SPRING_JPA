package configuracao;

import java.util.Properties;

import org.springframework.beans.factory.config.YamlProcessor.ResolutionMethod;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class YamlFileApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
	    Resource yamlResource = new ClassPathResource("application.yml");
	    yaml.setResources(yamlResource);
	    yaml.setResolutionMethod(ResolutionMethod.FIRST_FOUND);
	    Properties properties = yaml.getObject();
		
	    PropertySource<?> yamlTestProperties = new PropertiesPropertySource("applicationProperties", properties);
	    applicationContext.getEnvironment().getPropertySources().addFirst(yamlTestProperties);
	}
	
}