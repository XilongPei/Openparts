package com.openparts.base.controller;

import com.openparts.base.entity.Car;
import com.openparts.base.service.CarService;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.pojo.TreeNode;
import com.cnpc.framework.constant.RedisConstant;
import com.cnpc.framework.utils.StrUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

    @Resource
    private CarService carService;

    @RequestMapping(method = RequestMethod.GET, value = "/tree")
    private String list() {

        return "base/car/car_tree";
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public List<Car> getAll() {

        String hql = "from Car order by levelCode asc";
        return carService.find(hql.toString());
    }

}
