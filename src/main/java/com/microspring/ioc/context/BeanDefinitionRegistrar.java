package com.microspring.ioc.context;

import com.microspring.commons.utils.ClassUtils;
import com.microspring.commons.utils.ValidationUtils;
import com.microspring.ioc.bean.BeanDefinition;
import com.microspring.ioc.bean.annotation.Bean;
import com.microspring.ioc.bean.annotation.ComponentScan;
import com.microspring.ioc.bean.annotation.ComponentScans;
import com.microspring.ioc.bean.annotation.Configuration;
import com.microspring.ioc.bean.annotation.Import;
import com.microspring.ioc.bean.annotation.TargetAnnotation;
import com.microspring.ioc.container.BeanContainer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanDefinition注册器
 * 将BeanDefinition注册到底层容器
 *
 * @author czf
 * @Date 2020/5/9 12:42 下午
 */
public class BeanDefinitionRegistrar {

    protected final Log log = LogFactory.getLog(getClass());
    // 用于存储已经注册过的类
    public static Set<Class<?>> ClassesHasRegistered
            = Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    private BeanContainer container;

    public BeanDefinitionRegistrar() {
        container = BeanContainer.getInstance();
    }

    void doRegister(String beanName, BeanDefinition beanDefinition) {
        container.doRegister(beanName, beanDefinition);
    }

    void registerBean(Class<?> clazz) {
        // 根据class对象解析得到BeanDefinition
        BeanDefinition bd = new BeanDefinition(clazz);

        // 检查是否是@Configuration类
        if (clazz.isAnnotationPresent(Configuration.class)) {
            registerConfigurationBean(clazz);
        }

        for (Class<? extends Annotation> ScanPackageAnnotationklass : TargetAnnotation.COMPONENT_SCAN_ANNOTATION) {
            // 只要关于扫描包的注解，就去扫描并注册
            if (clazz.isAnnotationPresent(ScanPackageAnnotationklass)) {
                invokePackageScanning(clazz.getAnnotation(ScanPackageAnnotationklass));
            }
            // 是否是EnableAspectJAutoProxy
            if (clazz.isAnnotationPresent(TargetAnnotation.Enable_AspectJ_AutoProxy)) {
                // 遍历这个注解上的所有注解（一定有Import）
                for (Annotation annotation : TargetAnnotation.Enable_AspectJ_AutoProxy.getAnnotations())
                    invokePackageScanning(annotation);
            }
        }

        // 注册
        doRegister(bd.getBeanName(), bd);
    }

    private void registerConfigurationBean(Class<?> configClass) {
        try {
            Object configInstance = configClass.getDeclaredConstructor().newInstance();

            // 获取所有带有@Bean注解的方法
            for (Method method : configClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Bean.class)) {
                    // 调用方法获取Bean实例
                    Object beanInstance = method.invoke(configInstance);

                    // 创建BeanDefinition
                    BeanDefinition bd = new BeanDefinition(beanInstance.getClass());
                    bd.setBeanInstance(beanInstance);

                    // 注册Bean
                    doRegister(method.getName(), bd);
                }
            }
        } catch (Exception e) {
            log.error("Failed to register configuration bean", e);
        }
    }

    private void invokePackageScanning(Annotation annotation) {
        if (annotation instanceof ComponentScan) {
            scanPackageToRegister((ComponentScan) annotation);
        } else if (annotation instanceof ComponentScans) {
            // ComponentScans的value就是一个ComponentScan的数组
            // 扫描这个数组
            ComponentScan[] scanners = ((ComponentScans) annotation).value();
            for (ComponentScan scanner : scanners) {
                scanPackageToRegister(scanner);
            }
        } else if (annotation instanceof Import) {
            Class<?>[] classes = ((Import) annotation).value();
            for (Class<?> clazz : classes)
                registerBean(clazz);
        }
    }

    /**
     * 从ComponentScan获取包名，并注册Bean
     *
     * @param annotation
     */
    private void scanPackageToRegister(ComponentScan annotation) {
        String packageName = annotation.value();
        if (ValidationUtils.isEmpty(packageName)) {
            log.warn("包名不正确...");
            return;
        }
        Set<Class<?>> classes = ClassUtils.scanPackage(packageName, ClassesHasRegistered);
        if (classes != null) {
            for (Class<?> clazz : classes) {
                for (Class<? extends Annotation> beanAnnotationClass : TargetAnnotation.BEAN_ANNOTATION)
                    if (clazz.isAnnotationPresent(beanAnnotationClass)) {
                        registerBean(clazz);
                        break;
                    }
            }
        }
    }

    public void register(Class<?>... annotatedClasses) {
        if (annotatedClasses == null) return;
        for (Class<?> clazz : annotatedClasses)
            registerBean(clazz);
    }
}
