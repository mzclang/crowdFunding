package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

	User queryUserlogin(Map<String, Object> paramMap);
//
//	List<User> queryList(@Param("startIndex") Integer startIndex,@Param("pagesize") Integer pagesize);
//
//	Integer queryCount();

	List<User> queryList(Map<String, Object> paramMap);

	Integer queryCount(Map<String, Object> paramMap);

	List<Role> queryAllRole();

	List<Integer> queryRoleByUserid(Integer id);

	int saveUserRoleRelationship(@Param("userid") Integer userid,@Param("data") Data data);

	int deleteUserRoleRelationship(@Param("userid") Integer userid,@Param("data") Data data);

	//int deleteBatchUserByVO(Data data);

	int deleteBatchUserByVO(List<User> datas);

	List<Permission> queryPermissionByUserid(Integer id);


	

}