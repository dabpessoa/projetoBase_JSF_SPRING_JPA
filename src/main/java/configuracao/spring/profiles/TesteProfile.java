package configuracao.spring.profiles;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariDataSource;

import configuracao.ApplicationEnvironment;
import configuracao.spring.profiles.annotations.Teste;

@Teste
public class TesteProfile {

    @Autowired
    private Properties activeProperties;

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(activeProperties.getProperty("jdbc.postgres.driver"));
        ds.setJdbcUrl(activeProperties.getProperty("jdbc.postgres.url"));
        ds.setUsername(activeProperties.getProperty("jdbc.postgres.username"));
        ds.setPassword(activeProperties.getProperty("jdbc.postgres.password"));

        // Pooled Configurations.
        ds.addDataSourceProperty("cachePrepStmts", "true");
        ds.addDataSourceProperty("prepStmtCacheSize", "250");
        ds.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds.setConnectionTimeout(20000); // Default 30000 (30 seconds)
        ds.setIdleTimeout(600000); // Default 600000 (10 minutes)
        ds.setMaxLifetime(1800000); // Default 1800000 (30 minutes)
        ds.setMaximumPoolSize(15); // Default 10
        ds.setPoolName(activeProperties.getProperty("applicationName")+"-connection-pool");

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
        jpaProperties.setProperty("hibernate.dialect", activeProperties.getProperty("hibernate.postgres.dialect"));
        jpaProperties.setProperty("cache.provider_class", activeProperties.getProperty("cache.provider_class"));
        jpaProperties.setProperty("hibernate.generate_statistics", activeProperties.getProperty("hibernate.generate_statistics"));
        jpaProperties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", activeProperties.getProperty("hibernate.temp.use_jdbc_metadata_defaults"));
        jpaProperties.setProperty("hibernate.format_sql", activeProperties.getProperty("hibernate.format_sql"));
        jpaProperties.setProperty("hibernate.show_sql", activeProperties.getProperty("hibernate.show_sql"));
        jpaProperties.setProperty("hibernate.jdbc.batch_size", "8");
        localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);

        return localContainerEntityManagerFactoryBean;
    }

}
