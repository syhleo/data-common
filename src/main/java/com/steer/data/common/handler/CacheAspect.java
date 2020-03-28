package com.steer.data.common.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.steer.data.common.annotation.NeedCacheAop;
import com.steer.data.common.config.GlobalMap;
import com.steer.data.common.constants.CommonConstants;
import com.steer.data.common.redis.RedisUtil;
import com.steer.data.common.utils.SerializeUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于对相应的请求接口切入缓存存取的相关逻辑，使用AOP可以对代码0侵入性，是一个很好的方法
 */
@Component
@Aspect
@ConditionalOnExpression("#{'true'.equals(environment['redis.enabled'])}")
public class CacheAspect {

    public static final Logger log = LoggerFactory.getLogger(CacheAspect.class);   //
    @Autowired
    RedisUtil redisService;


    /**
     * 项目-无侵入代码方式使用Redis实现缓存功能（https://blog.csdn.net/csdn___lyy/article/details/102484922）
     * Redis无侵入式地缓存业务查询数据（https://blog.csdn.net/Allen_jinjie/article/details/79094054）
     */
    
    /**设置切入点*/
    //方法上面有@NeedCacheAop的方法,增加灵活性
    @Pointcut("@annotation(com.steer.data.common.annotation.NeedCacheAop)")
    public void annotationAspect(){}
//    //相应包下所有以XcarIndex开头的类中的所有方法，减少代码侵入性
//    @Pointcut("execution(public * com.xcar.data.web.backend.controller.XcarIndex*.*(..))")
//    public void controllerAspect(){}
    //相应包下所有以Cache结尾的类中的所有方法，减少代码侵入性
    @Pointcut("execution(public * com.steer.data..*.*Cache.*(..))")
    public void controllerAspect(){}


    /**环绕通知*/
    @Around(value = "controllerAspect()||annotationAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint){
        log.info("缓存操作处理...");
        //从缓存中取Redis状态
        String redis_alive = GlobalMap.conMap.get(CommonConstants.REDIS_ENABLED);
        if(CommonConstants.DISABLED.equals(redis_alive)){//Redis服务出现异常
            try{
                return  joinPoint.proceed();//执行接口调用的方法并获取返回值
            }catch (Throwable ex){
                ex.printStackTrace();
            }
        }


        //获取请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        //存储接口返回值
        Object object = new Object();

        //获取注解对应配置过期时间
        NeedCacheAop cacheAop = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(NeedCacheAop.class);  //获取注解自身
        String nxxx;String expx;long time;
        if (cacheAop == null){//规避使用第二种切点进行缓存操作的情况
            log.info("属于以Cache结尾的情况");
            nxxx = "nx";
            expx = "ex";
            time = 30*60;  //默认过期时间为30分钟
        }else{
            log.info("当前方法设置了NeedCacheAop注解");
            nxxx = cacheAop.nxxx();
            expx = cacheAop.expx();
            time = cacheAop.time();
        }
        //获取key
        String key = redisService.getKeyForAop(joinPoint,request);
        if (redisService.hasKey(key)){
            String obj = redisService.get(key)+"";
            if ("fail".endsWith(obj)){  //规避redis服务不可用
                try {
                    //执行接口调用的方法
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }else{
                Signature signature =  joinPoint.getSignature();
                Class returnType = ((MethodSignature) signature).getReturnType();
                //System.out.println(returnType.getCanonicalName());
                if("java.lang.String".equals(returnType.getName()) || "java.lang.Integer".equals(returnType.getName())
                   || "java.lang.Long".equals(returnType.getName()) || "java.lang.Double".equals(returnType.getName()) || "java.lang.Float".equals(returnType.getName()) ){
                    return obj;
                }else if("java.util.List".equals(returnType.getName())){
                    JSONArray klass = SerializeUtil.unserializeArray(obj);
                    return JSONObject.parseArray(klass.toJSONString(),Object.class);
                }else{
                    JSONObject klass =  SerializeUtil.unserializeObject(obj);
                    return JSONObject.toJSON(klass);
                }
//                try{
//                    JSONObject klass =  SerializeUtil.unserializeObject(obj);
//                    return JSONObject.toJSON(klass);
//                    //return new ResponseEntity<>(klass.get("body"), HttpStatus.OK) ;
//                }catch (Exception e){
//                    JSONArray klass = SerializeUtil.unserializeArray(obj);
//                    return JSONObject.parseArray(klass.toJSONString(),Object.class);
//                }
            }
        }else{
            try {
                //执行接口调用的方法并获取返回值   其中joinPoint.proceed()表示执行接口调用的方法
                object = joinPoint.proceed();
                if(object!=null){
                    Signature signature =  joinPoint.getSignature();
                    Class returnType = ((MethodSignature) signature).getReturnType();
                    if("java.lang.String".equals(returnType.getName()) || "java.lang.Integer".equals(returnType.getName())
                            || "java.lang.Long".equals(returnType.getName()) || "java.lang.Double".equals(returnType.getName()) || "java.lang.Float".equals(returnType.getName()) ){
                        redisService.set(key,object,time);//单位默认是秒
                    }else{
                        String serobj = SerializeUtil.serializeObject(object);
                        //redisService.set(key,serobj,nxxx,expx,time);
                        redisService.set(key,serobj,time);//单位默认是秒
                    }
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return object;
    }
}
