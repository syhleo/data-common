package com.steer.data.db.datahandling.controller;

import com.steer.data.common.result.ResultModel;
import com.steer.data.db.datahandling.service.impl.SqlHandlerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SqlHandler")
public class SqlHandlerController {

    @Autowired
    SqlHandlerServiceImpl sqlHandlerService;

    @GetMapping("/datatrans")
    public ResultModel dataTrans(@RequestParam String wrk_date) {
        String result=sqlHandlerService.doWork(wrk_date);
        return ResultModel.success(result);
    }
}
