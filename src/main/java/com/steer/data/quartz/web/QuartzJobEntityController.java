package com.steer.data.quartz.web;


import com.steer.data.common.result.ResultModel;
import com.steer.data.common.result.ResultModelUtil;
import com.steer.data.quartz.model.QuartzJobEntity;
import com.steer.data.quartz.service.impl.QuartzJobEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * quartz定时器动态执行的任务信息 前端控制器
 * </p>
 *
 * @author syhleo
 * @since 2019-09-16
 */
@RestController
@RequestMapping("/quartzJobEntity")
public class QuartzJobEntityController {

    @Autowired
    private QuartzJobEntityServiceImpl quartzJobEntityService;
    //private QuartzJobEntityService quartzJobEntityService;//会报错
//    @Autowired
//    private CategoryService categoryService;


    @GetMapping("/list")
    public ResultModel list(){
        List<QuartzJobEntity> quartzJobEntityList=quartzJobEntityService.list();
        System.out.println("QuartzJobEntityController的list方法。。。");
        return ResultModelUtil.success(quartzJobEntityList);
    }

    @PostMapping("/listForModel")
    public ResultModel listJobs(@RequestBody QuartzJobEntity quartzJobEntity){
        List<QuartzJobEntity> quartzJobEntityList=quartzJobEntityService.listForModel(quartzJobEntity);
        System.out.println("getDbCache。。。。方法");
        return ResultModelUtil.success(quartzJobEntityList);
    }

    @GetMapping("/listForName")
    public ResultModel listJobForName(@RequestParam String jobname){
        List<QuartzJobEntity> quartzJobEntityList=quartzJobEntityService.listForName(jobname);
        System.out.println("listJobForName。。。。方法");
        return ResultModelUtil.success(quartzJobEntityList);
    }

}

