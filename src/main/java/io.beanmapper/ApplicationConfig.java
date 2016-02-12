package io.beanmapper;

import io.beanmapper.config.Profile;
import io.beanmapper.config.WebMvcConfig;
import io.beanmapper.spring.converter.IdToEntityBeanConverter;
import io.beanmapper.support.beanmapper.AdvancedBeanUnproxy;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackageClasses = ApplicationConfig.class,
        excludeFilters = {
                @Filter({ ControllerAdvice.class, Controller.class, RestController.class }),
                @Filter(value = WebMvcConfig.class, type = FilterType.ASSIGNABLE_TYPE)
        })
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = ApplicationConfig.class)
public class ApplicationConfig {

    @Autowired private Profile profile;
    @Autowired private ApplicationContext applicationContext;

    @Bean
    public BeanMapper beanMapper() {
        BeanMapper bm = new BeanMapper();
        bm.addPackagePrefix(ApplicationConfig.class);
        bm.setBeanUnproxy(new AdvancedBeanUnproxy());
        if (applicationContext != null) {
            bm.addConverter(new IdToEntityBeanConverter(applicationContext));
        }
        return bm;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(profile.dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(ApplicationConfig.class.getPackage().getName());
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(false);
        jpaVendorAdapter.setDatabasePlatform(profile.hibernateDialect());
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.show_sql", Boolean.TRUE);
        jpaProperties.put("hibernate.ejb.naming_strategy", ImprovedNamingStrategy.class.getName());
        jpaProperties.put("hibernate.dialect", profile.hibernateDialect());
        jpaProperties.put("hibernate.hbm2ddl.auto", profile.hibernate2Ddl());
        jpaProperties.put("hibernate.jdbc.use_get_generated_keys", true);
        jpaProperties.put("hibernate.id.new_generator_mappings", true);
        jpaProperties.put("hibernate.generate_statistics", false);
        jpaProperties.put("javax.persistence.validation.factory", validator());

        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
