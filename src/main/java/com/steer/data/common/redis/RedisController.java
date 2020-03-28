package com.steer.data.common.redis;

import com.steer.data.common.annotation.NeedCacheAop;
import com.steer.data.common.result.ResultModel;
import com.steer.data.common.result.ResultModelUtil;
import com.steer.data.quartz.model.QuartzJobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    RedisUtil redisUtil;

    /**
     * @RequestMapping如果没有指定请求方式，将接收Get,Post,Head,Options等所有的请求方式
     * method 不写的话，默认GET、POST都支持，根据前端方式自动适应
     * @RequestMapping 不写method 为get,post方式都支持
     *
     * @GetMapping("/action5")   //这是一个组合注解等同下面这个注解
     * @RequestMapping(value = "/action5",method = RequestMethod.GET)
     * @RequestMapping(value = "/action7",produces="application/json; charset=UTF-8")
     * https://blog.csdn.net/qq_42218123/article/details/80526520
     *
     *
     * @return
     */
    @RequestMapping("/set")
    public ResultModel redisSet(){
        QuartzJobEntity qje=new QuartzJobEntity();
        qje.setCron("coreleo2");
        qje.setCronDesc("描述");
        qje.setHttpIds("2,4");
        redisUtil.set("1",qje,60);
        return ResultModel.success("成功");
    }

    @RequestMapping("/get")
    public ResultModel redisGet(@RequestParam String key){
        return ResultModelUtil.success( redisUtil.get(key));// {"code":0,"msg":"success","data":{"id":1,"name":"2018081001"}}
    }

    @NeedCacheAop
    @GetMapping("/getDbCache")
    //@RequestMapping(value = "/getDbCache",method = RequestMethod.GET)
    public ResultModel getDbCache(){
        System.out.println("getDbCache...方法");
        return ResultModelUtil.success("leoleo");
    }

}
