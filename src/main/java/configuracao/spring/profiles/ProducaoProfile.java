package configuracao.spring.profiles;

import org.springframework.context.annotation.PropertySource;

import configuracao.ApplicationEnvironment;
import configuracao.YamlPropertyLoaderFactory;
import configuracao.spring.profiles.annotations.Producao;

/**
 *
 * Created by diego.pessoa on 07/03/2017.
 */
@Producao
@PropertySource(value = "classpath:"+ApplicationEnvironment.PROPERTY_SOURCE_PRODUCAO_NAME
				,name = "applicationProducaoProperties"
				,factory = YamlPropertyLoaderFactory.class)
public class ProducaoProfile {

	

}
