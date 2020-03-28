package com.steer.data.common.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.steer.data.common.constants.CommonConstants;
import com.steer.data.common.exception.CustomException;
import com.steer.data.common.result.ResultModel;
import com.steer.data.common.utils.CookieUtil;
import com.steer.data.common.utils.IllegalStrFilterUtil;
import com.steer.data.quartz.model.LoggingEvent;
import com.steer.data.quartz.service.impl.LoggingEventServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * @Author: syhleo
 * @Description: 调用接口打印性能日志以及接口报错之后记录错误日志
 * @Date: 2019/9/20
 * @Time: 15:16
 */
//@Slf4j
@Component
@Aspect
public class CustomMessageLog {

    //public static final Logger logger = LoggerFactory.getLogger(CustomMessageLog.class);   使用@Slf4j注解代替了。
    public static final Logger log = LoggerFactory.getLogger(CustomMessageLog.class);   //使用@Slf4j注解代替了。
    //private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(CustomMessageLog.class);
    //private static final String LOG_CONTENT = "[类名]:%s,[方法]:%s,[参数]:%s";
    private static final String LOG_CONTENT = "[类名]:%s,[方法]:%s";
    private static final String[] METHOD_CONTENT = {"insert", "delete", "update", "save", "select"};


    @Value("${dc.log.bad.value:5000}")
    private int performanceBadValue;

//    @Resource
//    private RabbitMQService rabbitMQService;

    @Autowired
    LoggingEventServiceImpl loggingEventService;

    /*
    https://blog.csdn.net/somilong/article/details/74568223
    AOP(execution表达式)
     execution(* com..*.*Dao.find*(..))l
     匹配包名前缀为com的任何包下类名后缀为Dao的方法，方法名必须以find为前缀。
     如com.baobaotao.UserDao#findByUserId()、com.baobaotao.dao.ForumDao#findById()的方法都匹配切点。
     */
    /**
     * Object proceed() throws Throwable //执行目标方法
     * Object proceed(Object[] var1) throws Throwable //传入的新的参数去执行目标方法,即用新的参数值执行目标方法
     */

    //@Pointcut("execution(* com.steer.data..*.*(..))")  //匹配 com.steer.data下面包及子包的所有方法
    @Pointcut("execution(* com.steer.data..*.*Controller.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();
        //ResultModel ResultModel;
        Object obj;
        try {
            log.info("执行Controller开始: " + pjp.getSignature() + " 参数：" + Lists.newArrayList(pjp.getArgs()).toString());
            //处理入参特殊字符和sql注入攻击
            checkRequestParam(pjp);
            //执行访问接口操作    执行接口调用的方法并获取返回值   其中pjp.proceed(pjp.getArgs())表示执行接口调用的方法
            obj = pjp.proceed(pjp.getArgs());
            try {
                //此处将日志打印放入try-catch是因为项目中有些对象实体bean过于复杂，导致序列化为json的时候报错，但是此处报错并不影响主要功能使用，只是返回结果日志没有打印，所以catch中也不做抛出异常处理
                String result = JSONObject.toJSONString(obj);//接口的返回值
                log.info("执行Controller结束: " + pjp.getSignature() + "， 返回值：" + result);
                if (obj instanceof ResultModel) {
                    ResultModel rm = (ResultModel) obj;
                    if ("500".equals(rm.getState())) {
                        saveLog(pjp, new Exception(rm.getMsgInfo()), result);
                    } else {
                        saveLog(pjp, null, result);
                    }
                } else {
                    saveLog(pjp, null, result);
                }
            } catch (Exception ex) {
                log.error(pjp.getSignature() + " 接口记录返回结果失败！，原因为：{}", ex.getMessage());
            }
            Long consumeTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            log.info("耗时：" + consumeTime + "(毫秒).");
            //当接口请求时间大于5秒时，标记为异常调用时间，并记录入库
            if (consumeTime > performanceBadValue) {
//                DcPerformanceEntity dcPerformanceEntity = new DcPerformanceEntity();
//                dcPerformanceEntity.setInterfaceName(pjp.getSignature().toString());
//                dcPerformanceEntity.setRequestParam(Lists.newArrayList(pjp.getArgs()).toString());
//                dcPerformanceEntity.setConsumeTime(consumeTime + "毫秒");
                //RabbitMQMessageTarget mqTarget = RabbitMQMessageTarget.createFanoutTarget(ProjectConstants.DC_KEY_EXCHANGE_PERFORMANCE, new String[] { ProjectConstants.DC_KEY_QUEUE_PERFORMANCE});
                //rabbitMQService.send(mqTarget, JSON.toJSONString(dcPerformanceEntity));
                log.error("接口请求时间大于5秒，标记为异常调用时间，并记录入库");
            }
        } catch (Exception throwable) {
            obj = handlerException(pjp, throwable);
            saveLog(pjp, throwable, null);
        }
        return obj;
    }

    /**
     * 保存日志到数据库
     *
     * @param joinPoint
     * @param exception
     * @author syhleo
     * @date
     */
    private void saveLog(JoinPoint joinPoint, Exception exception, String result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String methodName = joinPoint.getSignature().getName();// 方法名
        Method method = currentMethod(joinPoint, methodName);
        //OperationLogger log = method.getAnnotation(OperationLogger.class);
        String className = joinPoint.getTarget().getClass().getName();//类名
        LoggingEvent logs = new LoggingEvent();
        logs.setCreate_time(new Timestamp(new Date().getTime()));
        logs.setCaller_clamth(String.format(LOG_CONTENT, className, methodName));
        logs.setCreate_opr(null);
        logs.setDirection(null);

        logs.setIp_addr(CookieUtil.ipAddress());
        logs.setResult_desc(result);//接口返回值描述
        logs.setSpec_desc(null);
        //logs.setContent(operateContent(joinPoint, methodName, request));
        logs.setParams_desc(operateContent(joinPoint, methodName, request));
        if (exception != null) {
            log.error("出现异常：" + exception.toString());
            logs.setEvent_flag("0");
            logs.setErr_msg(exception.toString());
            loggingEventService.insert(logs);
        } else {
            logs.setEvent_flag("1");
//             //判断是哪些方法可以写入LOG
//            if (isWriteLog(methodName)) {
//                logs.setOperation((log != null) ? log.description() : null);
//                logService.insert(logs);
//            }
            loggingEventService.insert(logs);
        }
    }

    /**
     * 获取当前传递的参数
     *
     * @param joinPoint  连接点
     * @param methodName 方法名称
     * @return 操作内容
     */
    private String operateContent(JoinPoint joinPoint, String methodName, HttpServletRequest request) {
        String className = joinPoint.getTarget().getClass().getName();
        Object[] params = joinPoint.getArgs();

        StringBuffer bf = new StringBuffer();
        if (params != null && params.length > 0) {
            Enumeration<String> paraNames = request.getParameterNames();
            while (paraNames.hasMoreElements()) {
                String key = paraNames.nextElement();
                bf.append(key).append("=");
                bf.append(request.getParameter(key)).append("&");
            }
            if (StringUtils.isBlank(bf.toString())) {
                if (request.getQueryString() != null) {
                    bf.append(request.getQueryString());
                } else {
                    for (Object obj : params) {
                        bf.append(JSONObject.toJSONString(obj));
                    }
                }
            }


        }
        //return String.format(LOG_CONTENT, className, methodName, bf.toString());
        return bf.toString();
    }

    /**
     * 判断是哪些方法可以写入LOG
     *
     * @param method
     * @return
     * @author Dingdong
     * @date 2017年5月24日
     */
    private boolean isWriteLog(String method) {
        boolean flag = false;
        for (String s : METHOD_CONTENT) {
            if (method.indexOf(s) > -1) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 获取当前执行的方法并判断
     *
     * @param joinPoint  连接点
     * @param methodName 方法名称
     * @return 方法
     */
    private Method currentMethod(JoinPoint joinPoint, String methodName) {
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Method resultMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }


//    //@Around("pointCut()")
//    public ResultModel handleControllerMethod2(ProceedingJoinPoint pjp) throws Throwable{
//        Stopwatch stopwatch = Stopwatch.createStarted();
//
//        ResultModel ResultModel;
//        try {
//            log.info("执行Controller开始: " + pjp.getSignature() + " 参数：" + Lists.newArrayList(pjp.getArgs()).toString());
//            //处理入参特殊字符和sql注入攻击
//            checkRequestParam(pjp);
//            //执行访问接口操作     --限定了接口的返回值必须为ResultModel类型
//            ResultModel = (ResultModel) pjp.proceed(pjp.getArgs());
//            try{
//                log.info("执行Controller结束: " + pjp.getSignature() + "， 返回值：" + JSONObject.toJSONString(ResultModel));
//                //此处将日志打印放入try-catch是因为项目中有些对象实体bean过于复杂，导致序列化为json的时候报错，但是此处报错并不影响主要功能使用，只是返回结果日志没有打印，所以catch中也不做抛出异常处理
//            }catch (Exception ex){
//                log.error(pjp.getSignature()+" 接口记录返回结果失败！，原因为：{}",ex.getMessage());
//            }
//            Long consumeTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
//            log.info("耗时：" + consumeTime + "(毫秒).");
//            //当接口请求时间大于3秒时，标记为异常调用时间，并记录入库
//            if(consumeTime > performanceBadValue){
////                DcPerformanceEntity dcPerformanceEntity = new DcPerformanceEntity();
////                dcPerformanceEntity.setInterfaceName(pjp.getSignature().toString());
////                dcPerformanceEntity.setRequestParam(Lists.newArrayList(pjp.getArgs()).toString());
////                dcPerformanceEntity.setConsumeTime(consumeTime + "毫秒");
//                //RabbitMQMessageTarget mqTarget = RabbitMQMessageTarget.createFanoutTarget(ProjectConstants.DC_KEY_EXCHANGE_PERFORMANCE, new String[] { ProjectConstants.DC_KEY_QUEUE_PERFORMANCE});
//                //rabbitMQService.send(mqTarget, JSON.toJSONString(dcPerformanceEntity));
//                log.error("接口请求时间大于3秒，标记为异常调用时间，并记录入库" );
//            }
//        } catch (Exception throwable) {
//            ResultModel = handlerException(pjp, throwable);
//        }
//
//        return ResultModel;
//    }


    /**
     * @Author: gmy
     * @Description: 处理接口调用异常
     * @Date: 15:13 2018/10/25
     */
    private ResultModel handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResultModel ResultModel;
        if (e.getClass().isAssignableFrom(CustomException.class)) {
            //CustomException为自定义异常类，项目中Controller层会把所有的异常都catch掉，并手工封装成CustomException抛出来，这样做的目的是CustomException会记录抛出异常接口的路径，名称以及请求参数等等，有助于错误排查
            CustomException customException = (CustomException) e;
            log.error("捕获到CustomException异常:", JSONObject.toJSONString(customException.getMessage()));
//            RabbitMQMessageTarget mqTarget = RabbitMQMessageTarget.createFanoutTarget(ProjectConstants.DC_KEY_EXCHANGE_INTERFACE_ERROR, new String[] { ProjectConstants.DC_KEY_QUEUE_INTERFACE_ERROR});
//            rabbitMQService.send(mqTarget, JSON.toJSONString(dataCenterException.getDcErrorEntity()));
            ResultModel = new ResultModel(CommonConstants.FAIL, null, customException.getMessage());
        } else if (e instanceof RuntimeException) {
            log.error("RuntimeException{方法：" + pjp.getSignature() + "， 参数：" + pjp.getArgs() + ",异常：" + e.getMessage() + "}", e);
            ResultModel = new ResultModel(CommonConstants.FAIL, null, e.getMessage());
        } else {
            log.error("异常{方法：" + pjp.getSignature() + "， 参数：" + pjp.getArgs() + ",异常：" + e.getMessage() + "}", e);
            ResultModel = new ResultModel(CommonConstants.FAIL, null, e.getMessage());
        }

        return ResultModel;
    }

    /**
     * @Author: gmy
     * @Description: 处理入参特殊字符和sql注入攻击
     * @Date: 15:37 2018/10/25
     */
    private void checkRequestParam(ProceedingJoinPoint pjp) {
        String str = String.valueOf(pjp.getArgs());
        if (!IllegalStrFilterUtil.sqlStrFilter(str)) {
            log.info("访问接口：" + pjp.getSignature() + "，输入参数存在SQL注入风险！参数为：" + Lists.newArrayList(pjp.getArgs()).toString());
            //DcErrorEntity dcErrorEntity = interfaceErrorService.processDcErrorEntity(pjp.getSignature() + "",Lists.newArrayList(pjp.getArgs()).toString(),"输入参数存在SQL注入风险!");
            //throw new DataCenterException(dcErrorEntity);
            throw new CustomException(Lists.newArrayList(pjp.getArgs()).toString() + "输入参数存在SQL注入风险!");
        }
        if (!IllegalStrFilterUtil.isIllegalStr(str)) {
            log.info("访问接口：" + pjp.getSignature() + ",输入参数含有非法字符!，参数为：" + Lists.newArrayList(pjp.getArgs()).toString());
//            DcErrorEntity dcErrorEntity = interfaceErrorService.processDcErrorEntity(pjp.getSignature() + "",Lists.newArrayList(pjp.getArgs()).toString(),"输入参数含有非法字符!");
//            throw new DataCenterException(dcErrorEntity);
            throw new CustomException(Lists.newArrayList(pjp.getArgs()).toString() + "输入参数含有非法字符!");
        }
    }

}
