package configuracao.spring.profiles;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariDataSource;

import configuracao.ApplicationEnvironment;
import configuracao.spring.profiles.annotations.Desenvolvimento;

/**
 * Created by diego.pessoa on 07/03/2017.
 */
@Desenvolvimento
public class DesenvolvimentoProfile {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(environment.getProperty("jdbc.postgres.driver"));
        ds.setJdbcUrl(environment.getProperty("jdbc.postgres.url"));
        ds.setUsername(environment.getProperty("jdbc.postgres.username"));
        ds.setPassword(environment.getProperty("jdbc.postgres.password"));

        // Pooled Configurations.
        ds.addDataSourceProperty("cachePrepStmts", "true");
        ds.addDataSourceProperty("prepStmtCacheSize", "250");
        ds.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds.setConnectionTimeout(20000); // Default 30000 (30 seconds)
        ds.setIdleTimeout(600000); // Default 600000 (10 minutes)
        ds.setMaxLifetime(1800000); // Default 1800000 (30 minutes)
        ds.setMaximumPoolSize(15); // Default 10
        ds.setPoolName(environment.getProperty("applicationName")+"-connection-pool");

        return ds;
    }
    
    @Bean
    @DependsOn("dataSource")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("postgresPU");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setPackagesToScan(ApplicationEnvironment.modelPackages);

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.dialect", environment.getProperty("hibernate.postgres.dialect"));
        jpaProperties.setProperty("cache.provider_class", environment.getProperty("cache.provider_class"));
        jpaProperties.setProperty("hibernate.generate_statistics", environment.getProperty("hibernate.generate_statistics"));
        jpaProperties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", environment.getProperty("hibernate.temp.use_jdbc_metadata_defaults"));
        jpaProperties.setProperty("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
        jpaProperties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        jpaProperties.setProperty("hibernate.jdbc.batch_size", "8");
        localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);

        return localContainerEntityManagerFactoryBean;
    }

}
