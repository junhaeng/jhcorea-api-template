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

/**
 * @Primary를 제거할 겨우 정상동작하지 않는다.
 * 이슈 상황. @Primary를 전부 제거 -> DataSource가 여러개여서 HibernateJpaConfigraution의 SingleCandination에 걸려서 builder 생성 실패
 * 이슈 상황2. 디버그 용도로 DataSource를 하나를 임의로 제거해본다. JpaBaseConfiguration의 웹컨피그 인터셉터부분의 빈관련 어노테이션에 실패한다.
 * 이슈 반만 해결: 모든 곳에 @Primary를 붙인다. 성공
 * 그러나 이는 autoconfiguration에서 사용하는 특정기능들이 하나의 datasource 및 jpa 관련 리소스들에 해당하는 기능으로 동작한다는 것이다.
 * 추후 이러한 기능을 사용하기 위해서는 autoconfiguration에 포함되어있는 것을 명시적으로 다시 재정의(구성) 해야 할 것이다.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.jhcorea.template.repository.writable"},
        entityManagerFactoryRef = "db1EntityManagerFactory",
        transactionManagerRef = "db1TransactionManager")
public class Db1JpaConfig {

    @Primary
    @Bean(destroyMethod = "close", name = "db1Datasource")
    @ConfigurationProperties(prefix = "heo-db1")
    public HikariDataSource db1DataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "db1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("db1Datasource") DataSource db1Datasource) {
        return builder
                .dataSource(db1Datasource)
                .properties(hibernateProperties())
                .packages("com.jhcorea.template.repository.entity")
                .persistenceUnit("template")
                .build();
    }

    @Primary
    @Bean(name = "db1TransactionManager")
    public PlatformTransactionManager db1TransactionManager(
            @Qualifier("db1EntityManagerFactory") EntityManagerFactory db1EntityManagerFactory) {
        return new JpaTransactionManager(db1EntityManagerFactory);
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
