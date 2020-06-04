package com.atguigu.atcrowdfunding.manager.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.dao.UserMapper;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.StringUtil;
import com.atguigu.atcrowdfunding.vo.Data;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//用户维护
		@RequestMapping("/index")
		public String toIndex(){	
			return "user/index";
		}
	
	//新增
	@RequestMapping("/toAdd")
	public String toAdd(){		
		return "user/add";
	}
	
	//新增
	  @ResponseBody
	  @RequestMapping("/doAdd")
	  public  Object doAdd(User user) {
		  AjaxResult result = new AjaxResult(); 
		  try { 
			  int count = userService.saveUser(user);
			  result.setSuccess(count==1);
			  
	  } catch (Exception e) {
		  result.setSuccess(false);
		  e.printStackTrace();
		  result.setMessage("数据增加失败"); 
	  } 
		  	return result ; //将对象序列化为JSON 的字符串，以流的形式返回 
	  }
	
	

	 
	//异步条件查询
	  @ResponseBody
	  @RequestMapping("/doindex")
	  public  Object index(@RequestParam(value="pageno",required = false ,defaultValue = "1")
	  Integer pageno,
	  @RequestParam(value="pagesize",required = false ,defaultValue = "10")Integer
	  pagesize,String queryText) {
		  AjaxResult result = new AjaxResult(); 
		  
		  try { 

			  Map<String,Object> paramMap = new HashMap<String,Object>();
			  paramMap.put("pageno",pageno);
			  paramMap.put("pagesize",pagesize);	
			  if(StringUtil.isNotEmpty(queryText)) {
				  if(queryText.contains("%")) {
					  queryText = queryText.replaceAll("%", "\\\\%");
				  }
				  paramMap.put("queryText",queryText);		 
			  }
			  Page page = userService.queryPage(paramMap); 
			  System.out.println(page);
			  result.setSuccess(true);
			  result.setPage(page); 	  
	  } catch (Exception e) {
		  result.setSuccess(false);
		  e.printStackTrace();
		  result.setMessage("数据查询失败"); 
	  }
		  return result;
	  }
	
	
	
	//删除dodeleteUser
		  @ResponseBody
		  @RequestMapping("/dodeleteUser")
		  public  Object deleteUser(Integer id) {
			  AjaxResult result = new AjaxResult(); 
			  try { 
				  int count = userService.deleteByPrimaryKey(id);
				  result.setSuccess(count==1);
				  
		  } catch (Exception e) {
			  result.setSuccess(false);
			  e.printStackTrace();
			  result.setMessage("数据删除失败"); 
		  } 
			  	return result ; //将对象序列化为JSON 的字符串，以流的形式返回 
		  }
	
		  
		  
		  //批量删除
		  //接收多条数据
		  @ResponseBody
		  @RequestMapping("/doDeleteBatch")
		  public  Object DeleteBatchUser(Data data) {
			  AjaxResult result = new AjaxResult(); 
			  try { 
				  int count = userService.DeleteBatchUsers(data);
				  result.setSuccess(count==data.getDatas().size());
				  
		  } catch (Exception e) {
			  result.setSuccess(false);
			  e.printStackTrace();
			  result.setMessage("数据删除失败"); 
		  } 
			  	return result ; //将对象序列化为JSON 的字符串，以流的形式返回 
		  }
		  
		  //批量删除
		  //接收一个参数名带多个值
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("/doDeleteBatch") public Object DeleteBatchUser(Integer[] id)
	 * { AjaxResult result = new AjaxResult(); try { int count =
	 * userService.DeleteBatchUser(id); result.setSuccess(count==id.length);
	 * 
	 * } catch (Exception e) { result.setSuccess(false); e.printStackTrace();
	 * result.setMessage("数据删除失败"); } return result ; //将对象序列化为JSON 的字符串，以流的形式返回 }
	 */
	
	//异步请求
	
//	  @ResponseBody
//	  @RequestMapping("/index")
//	  public  Object index(@RequestParam(value="pageno",required = false ,defaultValue = "1")
//	  Integer pageno,
//	  @RequestParam(value="pagesize",required = false ,defaultValue = "10")Integer
//	  pagesize) {
//		  AjaxResult result = new AjaxResult(); 
//		  
//		  try { 
//
//			  Page page = userService.queryPage(pageno,pagesize); 
//			  result.setSuccess(true);
//			  result.setPage(page); 
//			  
//	  } catch (Exception e) {
//		  result.setSuccess(false);
//		  e.printStackTrace();
//		  result.setMessage("数据查询失败"); 
//	  }
	  
	  
//	  return result ; //将对象序列化为JSON 的字符串，以流的形式返回 
//	  
//	  }
	 

	
	
	//同步请求
	
//	  @RequestMapping("/index") public String
//	  index(@RequestParam(value="pageno",required = false ,defaultValue = "1")
//	  Integer pageno,
//	  
//	  @RequestParam(value="pagesize",required = false ,defaultValue = "10")Integer
//	  pagesize,Map map) {
//	  
//	  Page page = userService.queryPage(pageno,pagesize);
//	  
//	  map.put("page",page);
//	  
//	  return "/user/index"; 
//	  }
	

	  
	  //修改
	  @RequestMapping("/toUpdate")
		public String toUpdate(Integer id,Map map){		
		  	User user = userService.getUserId(id);
		  	map.put("user",user);
			return "user/update";
		}
	  //修改
	  @ResponseBody
	  @RequestMapping("/doUpdate")
	  public  Object doUpdate(User user) {
		  AjaxResult result = new AjaxResult(); 
		  try { 
			  int count = userService.updateByPrimaryKey(user);
			  result.setSuccess(count==1);
			  
	  } catch (Exception e) {
		  result.setSuccess(false);
		  e.printStackTrace();
		  result.setMessage("数据修改失败"); 
	  } 
		  	return result ; //将对象序列化为JSON 的字符串，以流的形式返回 
	  }
	  
	  // 分配角色 doAssignRole
	  @ResponseBody
	  @RequestMapping("/doAssignRole")
	  public Object doAssignRole(Integer userid,Data data) {
		  AjaxResult result = new AjaxResult(); 
		  try { 
			  userService.saveUserRoleRelationship(userid,data);
			  result.setSuccess(true);
	  } catch (Exception e) {
		  result.setSuccess(false);
		  e.printStackTrace();
		  result.setMessage("数据修改失败"); 
	  } 
		  	return result ; //将对象序列化为JSON 的字符串，以流的形式返回 
	  }
	  
	  //取消角色 doUnAssignRole
	  @ResponseBody
	  @RequestMapping("/doUnAssignRole")
	  public Object doUnAssignRole(Integer userid,Data data) {
		  AjaxResult result = new AjaxResult(); 
		  try { 
			  userService.deleteUserRoleRelationship(userid,data);
			  result.setSuccess(true);
			  
	  } catch (Exception e) {
		  result.setSuccess(false);
		  e.printStackTrace();
		  result.setMessage("数据修改失败"); 
	  } 
		  	return result ; //将对象序列化为JSON 的字符串，以流的形式返回 
	  }
	  
	  //显示页面角色
	  @RequestMapping("/assignRole")
	  public String assignRole(Integer id,Map map) {
		  List<Role> allListRole = userService.queryAllRole();
		  
		  List<Integer> roleIds = userService.queryRoleByUserid(id); //查詢用戶的角色id
		  
		  List<Role> leftRoleList = new ArrayList<Role>(); //未分配角色
		  List<Role> rightRoleList = new ArrayList<Role>(); //已分配角色
		  
		  for (Role role : allListRole) {
			if(roleIds.contains(role.getId())) {
				rightRoleList.add(role);
				System.out.println(rightRoleList);
				
			}else {
				leftRoleList.add(role);
			}
		}
		 map.put("leftRoleList",leftRoleList);
		 map.put("rightRoleList",rightRoleList);
		 
		  return "user/assignrole";
	  }
}
