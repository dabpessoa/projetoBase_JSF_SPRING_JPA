package configuracao.spring.profiles;

import org.springframework.context.annotation.PropertySource;

import configuracao.ApplicationEnvironment;
import configuracao.YamlPropertyLoaderFactory;
import configuracao.spring.profiles.annotations.Teste;

@Teste
@PropertySource(value = "classpath:"+ApplicationEnvironment.PROPERTY_SOURCE_TESTE_NAME
				,name = "applicationTesteProperties"
				,factory = YamlPropertyLoaderFactory.class)
public class TesteProfile {

	
	
}
