package com.atguigu.atcrowdfunding.manager.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.exception.LoginFailException;
import com.atguigu.atcrowdfunding.manager.dao.UserMapper;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.MD5Util;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper ;

	@Override
	public User queryUserlogin(Map<String, Object> paramMap) {
		
		User user = userMapper.queryUserlogin(paramMap);
		
		if(user==null){
			throw new LoginFailException("用户账号或密码不正确!");
		}
		
		return user;
	}

	//异步条件查询
	@Override
	public Page queryPage(Map<String, Object> paramMap) {
		
		Page page = new Page((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
		
		Integer startIndex = page.getStartIndex();
		paramMap.put("startIndex",startIndex);
		
		List<User> datas = userMapper.queryList(paramMap);
	
		page.setDatas(datas);
		
		Integer totalsize =  userMapper.queryCount(paramMap);
		
		page.setTotalsize(totalsize);
		return page;
	}

	//增加用户
	@Override
	public int saveUser(User user) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String createtime = sdf.format(date);
		user.setCreatetime(createtime);
		
		user.setUserpswd(MD5Util.digest(Const.PASSWPRD));
		return userMapper.insert(user);
	}

	//修改
	@Override
	public User getUserId(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	//修改
	@Override
	public int updateByPrimaryKey(User record) {
		 
		return userMapper.updateByPrimaryKey(record);
	}

	//删除
	@Override
	public int deleteByPrimaryKey(Integer id) {
		
		return userMapper.deleteByPrimaryKey(id);
	}

	//批量删除
	/*
	@Override
	public int DeleteBatchUser(Integer[] ids) {
		int totalCount = 0 ;
		for (Integer id : ids) {
			int count = userMapper.deleteByPrimaryKey(id);
			totalCount += count;
		}
		return totalCount;
	}
*/
	
		//用戶角色
	@Override
	public List<Role> queryAllRole() {
		
		return userMapper.queryAllRole();
	}
	//角色id
	@Override
	public List<Integer> queryRoleByUserid(Integer id) {
		return userMapper.queryRoleByUserid(id);
	}

	//分配角色
	@Override
	public int saveUserRoleRelationship(Integer userid, Data data) {
		return userMapper.saveUserRoleRelationship(userid,data);
	}

	//取消角色
	@Override
	public int deleteUserRoleRelationship(Integer userid, Data data) {
		return userMapper.deleteUserRoleRelationship(userid, data);
	}

	//接收多条数据删除
	/*
	 * @Override public int DeleteBatchUsers(Data data) { return
	 * userMapper.deleteBatchUserByVO(data); }
	 */

	
	//接收多条数据删除
	@Override
	public int DeleteBatchUsers(Data data) {
		return userMapper.deleteBatchUserByVO(data.getDatas());
	}

	//权限页面展示
	@Override
	public List<Permission> queryPermissionByUserid(Integer id) {
		
		return userMapper.queryPermissionByUserid(id);
	}



	
	
	
	//同步数据查询
//	@Override
//	public Page queryPage(Integer pageno, Integer pagesize) {
//		Page page = new Page(pageno,pagesize);
//		
//		Integer startIndex = page.getStartIndex();
//		
//		List<User> datas = userMapper.queryList(startIndex,pagesize);
//	
//		page.setDatas(datas);
//		
//		Integer totalsize =  userMapper.queryCount();
//		
//		page.setTotalsize(totalsize);
//		return page;
//	}

	
}
