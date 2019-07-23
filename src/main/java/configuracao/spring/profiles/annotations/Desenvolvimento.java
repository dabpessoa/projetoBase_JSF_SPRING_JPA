package configuracao.spring.profiles.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import configuracao.ApplicationEnvironment;

/**
 * Created by diego.pessoa on 08/03/2017.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Profile(ApplicationEnvironment.PROFILE_DESENVOLVIMENTO)
@Configuration
public @interface Desenvolvimento {

}
