package com.atguigu.atcrowdfunding.manager.service;

import java.util.Map;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

public interface RoleService {

	Page queryRole(Map<String, Object> paramMap);

	int deleteByPrimaryKey(Integer id);

	int deleteByPrimaryKeys(Data data);

	int saveRole(Role record);
	
	Role getRole(Integer id);

	int updateByPrimaryKey(Role role);

	int saveRolePermissionRelationship(Integer roleid, Data datas);



}
