package com.steer.data.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {

    public static void main(String[] args){
        //初始化spring容器
        //AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(Appconfig.class);
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext();
        ac.register(AppConfig.class);
//        AbstractAutowireCapableBeanFactory beanFactory = (AbstractAutowireCapableBeanFactory)ac.getBeanFactory();
//        beanFactory.setAllowCircularReferences(false);//取消默认的循环依赖
        //在refresh方法之前可以做很多事情
        ac.refresh();



    }
}
