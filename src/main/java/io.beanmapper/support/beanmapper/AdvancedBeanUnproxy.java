package io.beanmapper.support.beanmapper;

import io.beanmapper.core.unproxy.BeanUnproxy;
import org.hibernate.proxy.HibernateProxy;

public class AdvancedBeanUnproxy implements BeanUnproxy {

    @Override
    public Class<?> unproxy(Class<?> beanClass) {
        String name = beanClass.getName();
        if (name.contains("$")) {
            Class<?> interfaceClass = beanClass.getInterfaces()[0];
            if (interfaceClass.equals(HibernateProxy.class)) {
                return beanClass.getSuperclass();
            } else {
                return beanClass.getInterfaces()[0];
            }
        }
        return beanClass;
    }
}
