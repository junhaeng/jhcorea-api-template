package com.jhcorea.template.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.jhcorea.template.repository.readonly"},
        entityManagerFactoryRef = "db1ReadOnlyEntityManagerFactory",
        transactionManagerRef = "db1ReadOnlyTransactionManager")
public class Db1ReadOnlyJpaConfig {

    @Bean(destroyMethod = "close", name = "db1ReadyOnlyDatasource")
    @ConfigurationProperties(prefix = "heo-db1-read-only")
    public HikariDataSource db1ReadOnlyDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "db1ReadOnlyEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean db1ReadOnlyEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("db1ReadyOnlyDatasource") DataSource db1ReadOnlyDataSource) {
        return builder
                .dataSource(db1ReadOnlyDataSource)
                .properties(hibernateProperties())
                .packages("com.jhcorea.template.repository.entity")
                .persistenceUnit("template_read_only")
                .build();
    }

    @Bean(name = "db1ReadOnlyTransactionManager")
    public PlatformTransactionManager db1ReadOnlyTransactionManager(
            @Qualifier("db1ReadOnlyEntityManagerFactory") EntityManagerFactory db1ReadOnlyEntityManagerFactory) {
        return new JpaTransactionManager(db1ReadOnlyEntityManagerFactory);
    }

    private Map<String, Object> hibernateProperties() {

        Resource resource = new ClassPathResource("hibernate.properties");
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            return properties.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> e.getValue())
                    );
        } catch (IOException e) {
            return new HashMap<String, Object>();
        }
    }
}
