package com.atguigu.atcrowdfunding.manager.service;

import java.util.List;

import com.atguigu.atcrowdfunding.bean.Permission;

public interface PermissionService {

	List<Permission> queryAllPermission();

	List<Integer> queryPermissionidsByRoleid(Integer roleid);

	int deletePermission(Integer id);

	int update(Permission per);

	Permission getPermission(Integer id);

	int addpermission(Permission per);

}
