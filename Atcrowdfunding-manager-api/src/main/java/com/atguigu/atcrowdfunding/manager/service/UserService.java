package com.atguigu.atcrowdfunding.manager.service;

import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

public interface UserService {

	User queryUserlogin(Map<String, Object> paramMap);

//	Page queryPage(Integer pageno, Integer pagesize);

	Page queryPage(Map<String, Object> paramMap);

	int saveUser(User user);

	User getUserId(Integer id);

	int updateByPrimaryKey(User record);
	
	int deleteByPrimaryKey(Integer id);

	//int DeleteBatchUser(Integer[] ids);

	List<Role> queryAllRole();

	List<Integer> queryRoleByUserid(Integer id);

	int saveUserRoleRelationship(Integer userid, Data data);

	int deleteUserRoleRelationship(Integer userid, Data data);

	int DeleteBatchUsers(Data data);

	List<Permission> queryPermissionByUserid(Integer id);

	

}
