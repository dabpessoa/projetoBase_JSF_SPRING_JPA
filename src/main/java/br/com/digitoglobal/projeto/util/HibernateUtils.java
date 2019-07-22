package br.com.digitoglobal.projeto.util;

import org.hibernate.Hibernate;

public class HibernateUtils {

    public static boolean isInitialized(Object proxy) {
        return Hibernate.isInitialized(proxy);
    }

    public static boolean isNotInitialized(Object proxy) {
        return !isInitialized(proxy);
    }

    public static void initialize(Object proxy) {
        Hibernate.initialize(proxy);
    }

}