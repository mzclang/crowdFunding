package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.RolePermission;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

	List<User> queryRoleList(Map<String, Object> paramMap);

	Integer queryRoleCount(Map<String, Object> paramMap);

	int deleteByPrimaryKeys(Data data);

	void deleteRolePermissionRelationship(Integer roleid);

	int insertRolePermission(RolePermission rp);
}