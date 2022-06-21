package cn.diyiliu.nacos;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.Map;

/**
 * SpringUtil
 * Spring 上下文工具类
 *
 * @author diyiliu
 * @create 2022-06-15 14:35
 **/
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {

        return applicationContext;
    }

    public static <T> T getBean(String name) throws BeansException {

        return (T) applicationContext.getBean(name);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {

        return applicationContext.getBeansOfType(type);
    }

    public static void init() {
        new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    public static void init(String filename) {
        new ClassPathXmlApplicationContext("classpath:" + filename);
    }

    public static void init(Class clazz) {
        new AnnotationConfigApplicationContext(clazz);
    }

    /**
     * 外部配置文件优先
     *
     * 将覆盖 jar 包内配置
     *
     * @param path
     * @param env
     * @return
     * @throws IOException
     */
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer(String path, ConfigurableEnvironment env) throws IOException {
        Resource res = new FileSystemResource(path);
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
        properties.setLocation(res);
        properties.setIgnoreResourceNotFound(true);

        if (res.exists()) {
            ResourcePropertySource resourcePropertySource = new ResourcePropertySource(res);
            env.getPropertySources().addFirst(resourcePropertySource);
        }
        return properties;
    }
}
