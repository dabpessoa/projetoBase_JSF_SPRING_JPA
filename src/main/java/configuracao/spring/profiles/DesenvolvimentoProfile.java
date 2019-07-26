package configuracao.spring.profiles;

import org.springframework.context.annotation.PropertySource;

import configuracao.ApplicationEnvironment;
import configuracao.YamlPropertyLoaderFactory;
import configuracao.spring.profiles.annotations.Desenvolvimento;

/**
 * Created by diego.pessoa on 07/03/2017.
 */
@Desenvolvimento
@PropertySource(value = "classpath:"+ApplicationEnvironment.PROPERTY_SOURCE_DESENVOLVIMENTO_NAME
				,name = "applicationDesenvolvimentoProperties"
				,factory = YamlPropertyLoaderFactory.class)
public class DesenvolvimentoProfile {

    

}
