package com.openparts.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.openparts.common.OP_Errors;

import com.openparts.utils.mongodb.GridFSClient;

@Controller
public class API_Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(API_Controller.class);

    @RequestMapping(value = "/api/{res}", method = RequestMethod.POST)
    public String do_API(@RequestParam(value = "access_token", required = false, defaultValue = "") String code, @PathVariable(value = "res") String res,
                           HttpServletRequest request, Model model) {
        if (res == null)
            return "";

        switch (res.toLowerCase()) {
            case "gettoken":
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String access_token;

                break;
            case "test":
                String[] args = null;

                try {
                    GridFSClient.main(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
        return OP_Errors.SUCCESS.toString();
    }
}
