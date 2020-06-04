package com.atguigu.atcrowdfunding.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.dao.PermissionMapper;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;

@Service
public class PermissionServiceImpl  implements PermissionService{

	@Autowired
	private PermissionMapper permissionMapper;
	
	//查找
	@Override
	public List<Permission> queryAllPermission() {
		return permissionMapper.queryAllPermission();
	}

	@Override
	public List<Integer> queryPermissionidsByRoleid(Integer roleid) {
		return permissionMapper.queryPermissionidsByRoleid(roleid);
	}

	//删除
	@Override
	public int deletePermission(Integer id) {
		return permissionMapper.deleteByPrimaryKey(id);
	}

	//修改
	@Override
	public int update(Permission per) {
		return permissionMapper.updateByPrimaryKey(per);
	}

	@Override
	public Permission getPermission(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	//增加
	@Override
	public int addpermission(Permission per) {
		return permissionMapper.insert(per);
	}

}
