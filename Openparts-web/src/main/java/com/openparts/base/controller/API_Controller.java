package com.openparts.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import com.openparts.common.OP_Errors;
import com.openparts.common.CommonConstants;
import com.openparts.utils.mongodb.GridFSClient;

@Controller
public class API_Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(API_Controller.class);

    @RequestMapping(value = "/api/{res}", method = RequestMethod.POST)
    @ResponseBody
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
                /*
                 * general test method
                 *
                 *   curl -d "className=class&methodName=method" -X POST http://localhost:8081/Openparts-web/api/test
                 *   curl -d "className=com.openparts.utils.mongodb.GridFSClient&methodName=testMain" -X POST http://localhost:8081/Openparts-web/api/test
                 */

                if (CommonConstants.DEBUG_RUNNING) {
                    /*
                    String[] args = null;

                    try {
                        GridFSClient.main(args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    */
                    try {
                        String className = request.getParameter("className");
                        String methodName = request.getParameter("methodName");
                        Class<?> objClass = Class.forName(className);
                        Method method = objClass.getMethod(methodName);
                        method.invoke(null);
                    } catch(Exception e) {
                        return OP_Errors.FAIL.toString();
                    }
                }
                break;
        }
        return OP_Errors.SUCCESS.toString();
    }
}
