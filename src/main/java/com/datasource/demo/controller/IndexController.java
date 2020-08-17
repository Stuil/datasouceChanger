package com.datasource.demo.controller;

import com.datasource.demo.service.IndexServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: IdenxController
 * @description:
 * @date: 2020/8/17
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version:
 */
@RestController
public class IndexController {

    @Autowired
    IndexServer indexServer;
    @RequestMapping("/index")
    public String index(Integer id){
       indexServer.syncInfo(id);
        return "1";
    }
    @RequestMapping("/index2")
    public String index2(){
        return indexServer.index();
    }
}
