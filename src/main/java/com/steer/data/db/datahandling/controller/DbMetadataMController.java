package com.steer.data.db.datahandling.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.steer.data.db.datahandling.service.DbMetadataMService;

/**
 * <p>
 * 数据库-元数据主表信息 前端控制器
 * </p>
 *
 * @author syhleo
 * @since 2019-09-16
 */
@Controller
@RequestMapping("/dbMetadataM")
public class DbMetadataMController {

    //@Autowired
    @Resource
    private DbMetadataMService dbMetadataMService;

    /**
     * 查询全部
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/listAll")
    public Object listAll() {
        return dbMetadataMService.listAll();
    }
}

