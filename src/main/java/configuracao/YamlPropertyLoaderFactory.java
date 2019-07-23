package configuracao;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlProcessor.ResolutionMethod;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

public class YamlPropertyLoaderFactory extends DefaultPropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        if (resource == null){
            return super.createPropertySource(name, resource);
        }

        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
	    yaml.setResources(resource.getResource());
	    yaml.setResolutionMethod(ResolutionMethod.FIRST_FOUND);
	    Properties properties = yaml.getObject();
		
	    return new PropertiesPropertySource("applicationProperties", properties);
    }
}