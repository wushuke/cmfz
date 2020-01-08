package com.baizhi.wsk.controller;

import com.baizhi.wsk.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/log")
public class LogController {
    @Resource
    LogService logService;

    //查所有日志
    @ResponseBody
    @RequestMapping("page")
    public Map page(Integer page, Integer rows){
        return  logService.page(page,rows);
    }

}
