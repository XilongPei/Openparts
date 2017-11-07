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
@RequestMapping("/console")
public class ConsoleController {

    @RequestMapping(method = RequestMethod.GET, value = "/dashboard")
    private String dashboard() {

        return "openparts/dashboard";
    }
}


