package configuracao;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlProcessor.ResolutionMethod;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import br.com.digitoglobal.projeto.util.DateUtils;

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
		
	    if (name == null || name.trim().isEmpty()) name = ApplicationEnvironment.APPLICATION_PROPERTY_SOURCE_NAME+"_"+(DateUtils.format(new Date(), "yyyyMMdd_HHmmss"));
	    return new PropertiesPropertySource(name, properties);
    }
}