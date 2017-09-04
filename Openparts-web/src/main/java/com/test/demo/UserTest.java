package com.test.demo;

import com.cnpc.framework.base.entity.Role;
import com.cnpc.framework.base.entity.User;
import com.cnpc.framework.base.service.RoleService;
import com.cnpc.framework.base.service.UserRoleService;
import com.cnpc.framework.base.service.UserService;
import com.cnpc.framework.testng.BaseTest;
import com.cnpc.framework.utils.EncryptUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.List;

public class UserTest extends BaseTest {

	@Resource
	private UserService userService;

	@Resource
	private RoleService roleService;

	@Resource
	private UserRoleService userRoleService;

	@Test(dataProvider ="dataProvider",groups = {"function-test"})
	public void initPassword(String password){
		List<User> userList=userService.find("from User");
		for (User user : userList) {
			user.setPassword(EncryptUtil.getPassword(password,user.getLoginName()));
			List<Role> roles=roleService.getRoleList(user.getId());
			if(roles.isEmpty()){
				userRoleService.setRoleForRegisterUser(user.getId());
			}
		}
		userService.batchUpdate(userList);
		Assert.assertEquals(1,1);
	}

	@Test(dataProvider = "dataProvider", groups = { "function-test" })
	public void checkUserExist(String code, String loginname) {
		System.out.println(code + "-----" + loginname);
		User user = userService.getUserByLoginName(loginname);
		Assert.assertNotNull(user);
	}
}

