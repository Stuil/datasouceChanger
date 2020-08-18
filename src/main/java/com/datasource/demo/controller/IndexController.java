package com.datasource.demo.controller;

import com.datasource.demo.entity.gas.GasUserEntity;
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
        long s1=System.currentTimeMillis()/1000L;
       // indexServer.syncInfo(id);
        long s5=System.currentTimeMillis()/1000L;
        return (s5-s1)+"--";
    }
    @RequestMapping("/index2")
    public String index2(){
        // indexServer.syncComm()
        return "null";
    }
    @RequestMapping("/sync")
    public String syncInfo(Integer id){
        return indexServer.syncCommAndConsumer(id);
    }
}
