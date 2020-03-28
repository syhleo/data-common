package com.steer.data.db.datahandling.controller;


import com.steer.data.common.exception.CustomException;
import com.steer.data.common.result.ResultModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class DemoController {
    /*
     * 地址： http://localhost:8080/demo/
     * 地址：http://localhost:7777/Miaosha-V1.0/demo/hello
     */
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "hello World！";
    }

    //	// rest api  json格式输出
    @RequestMapping("/hello")
    @ResponseBody
    public ResultModel hello() {
        //return ResultModel.success("你好，syh-测试部署后修改java文件。不行");
        throw new CustomException("这是测试自定义异常。。。。。");
    }
//	
//	@RequestMapping("/helloError")
//	@ResponseBody
//	public ResultModel<String> helloError(){
//		return ResultModel.error(CodeMsg.SERVER_ERROR);
//	}
//	
//	@RequestMapping("/thymeleaf")
//	public String thymeleaf(Model model){
//		model.addAttribute("name_y","songleo");
//		return "hello";
//	}

}
