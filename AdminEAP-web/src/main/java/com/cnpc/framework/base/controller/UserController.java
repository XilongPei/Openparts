package com.cnpc.framework.base.controller;

import com.alibaba.fastjson.JSON;
import com.cnpc.framework.annotation.RefreshCSRFToken;
import com.cnpc.framework.annotation.VerifyCSRFToken;
import com.cnpc.framework.base.entity.User;
import com.cnpc.framework.base.entity.UserAvatar;
import com.cnpc.framework.base.pojo.PageInfo;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.UserRoleService;
import com.cnpc.framework.base.service.UserService;
import com.cnpc.framework.utils.EncryptUtil;
import com.cnpc.framework.utils.PropertiesUtil;
import com.cnpc.framework.utils.StrUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    private final static String initPassword= PropertiesUtil.getValue("user.initPassword");

    /**
     * 用户编辑
     *
     * @return
     */
    @RefreshCSRFToken
    @RequestMapping(method = RequestMethod.GET, value = "/edit")
    private String edit(String id, HttpServletRequest request) {

        request.setAttribute("id", id);
        return "base/user/user_edit";
    }

    /**
     * 用户列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    private String list() {
        return "base/user/user_list";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/get")
    @ResponseBody
    private User getUser(String id) {

        return userService.get(User.class, id);
    }

    @VerifyCSRFToken
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @ResponseBody
    private Result saveUser(User user, HttpServletRequest request) {
        if (StrUtil.isEmpty(user.getId())) {
            //设置初始密码
            user.setPassword(EncryptUtil.getPassword(initPassword,user.getLoginName()));
            String userId = userService.save(user).toString();
            userRoleService.setRoleForRegisterUser(userId);
            //头像和用户管理
            userService.updateUserAvatar(user, request.getRealPath("/"));
        } else {
            User oldUser=this.getUser(user.getId());
            if(oldUser.getLoginName().equals(user.getLoginName())){
               oldUser.setPassword(EncryptUtil.getPassword(initPassword,user.getLoginName()));
            }
            BeanUtils.copyProperties(user,oldUser);
            oldUser.setUpdateDateTime(new Date());
            userService.update(oldUser);
        }
        return new Result(true);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete/{id}")
    @ResponseBody
    private Result deleteUser(@PathVariable("id") String id) {

        User user = userService.get(User.class, id);
        try {
            userService.delete(user);
        } catch (Exception e) {
            return new Result(false);
        }
        return new Result(true);
    }

    /**
     * loadData
     *
     * @param pInfo
     * @param conditions
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/loadData")
    @ResponseBody
    public Map<String, Object> loadData(String pInfo, String conditions) {

        Map<String, Object> map = new HashMap<String, Object>();
        PageInfo pageInfo = JSON.parseObject(pInfo, PageInfo.class);
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        pageInfo.setCount(userService.getCountByCriteria(criteria));
        map.put("pageInfo", pageInfo);
        map.put("data", userService.getListByCriteria(criteria, pageInfo));
        return map;
    }

    /**
     * tab方式curd demo
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tab/list")
    private String tablist() {

        return "base/user/user_tab_list";
    }


    /**
     * 新的页面打开 curd demo
     */
    @RequestMapping(method = RequestMethod.GET, value = "/page/list")
    private String pagelist(String id, HttpServletRequest request) {
        request.setAttribute("selectId", id);
        return "base/user/user_page_list";
    }

    /**
     * 用户编辑 new page
     *
     * @return
     */
    @RefreshCSRFToken
    @RequestMapping(method = RequestMethod.GET, value = "/page/edit")
    private String pageEdit(String id, HttpServletRequest request) {

        request.setAttribute("id", id);
        return "base/user/user_page_edit";
    }

    /**
     * 用户头像上传 avatar
     */
    @RequestMapping(method = RequestMethod.GET, value = "/avatar")
    private String avatar(String userId, HttpServletRequest request) {
        request.setAttribute("userId", userId);
        return "base/user/user_avatar";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/getAvatar")
    @ResponseBody
    private UserAvatar getAvatar(String userId) {
        return userService.getAvatarByUserId(userId);
    }

    @RequestMapping(value = "/select/{multiple}/{ids}/{callback}", method = RequestMethod.GET)
    public String selectUserPage(@PathVariable("multiple") String multiple,
                                 @PathVariable("ids") String ids,
                                 @PathVariable("callback") String callback, HttpServletRequest request) {
        request.setAttribute("multiple", multiple);
        request.setAttribute("ids", ids);
        request.setAttribute("callback",callback);
        return "base/user/user_select_list";
    }

    @RequestMapping(value = "/names", method = RequestMethod.POST)
    @ResponseBody
    public Map getNamesByIds(String ids) {
        Map<String,String> map=new HashMap<>();
            String names=userService.getUserNamesByUserIds(ids);
            map.put("name",names);
            return map;

    }
}
