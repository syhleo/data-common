package com.steer.data.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，用于标识方法是否需要使用缓存
 * 用在方法上面标识调用该方法的请求需要被缓存
 * 其中的nxxx、expx、time等参数是为了可以更灵活的空值缓存的方式与过期时间
 */
@Target({ElementType.PARAMETER, ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedCacheAop {
    //代表缓存策咯，nx:代表key不存在再进行缓存kv，xx:代表key存在再进行缓存kv  默认为"不存在key缓存key"
    String nxxx() default "nx";
    //代表过期时间单位，ex:秒 px:毫秒    默认为"秒"
    String expx() default "ex";
    //过期时间
    long time() default 30*60;
}

