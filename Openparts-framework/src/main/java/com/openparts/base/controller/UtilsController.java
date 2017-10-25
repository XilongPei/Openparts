package com.openparts.base.controller;

import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.openparts.common.utils.SmsUtils;
import javax.annotation.Resource;
import com.openparts.base.service.UtilsService;

@Controller
@RequestMapping("/utils")
public class UtilsController {

    @Resource
    public UtilsService utilsService;

    /*
     * send a verify code to mobile and store the code into database or redis
     */
    @RequestMapping(value = "/sendverifysms", method = RequestMethod.POST)
    @ResponseBody
    private String sendVerifySms(@PathVariable("mobile") String mobile, HttpServletRequest request) {

        String retStr;
        retStr = SmsUtils.sendSms(utilsService, mobile);

        return retStr;
    }
}


