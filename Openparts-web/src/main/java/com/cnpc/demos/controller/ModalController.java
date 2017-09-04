package com.cnpc.demos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by billJiang on 2017/1/29.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
@Controller
@RequestMapping(value="/modal")
public class ModalController {

    //modal的demo使用
    @RequestMapping(value = "/demo")
    public String showDemo(){
        return "hello world";
    }
}
