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

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.StringUtil;
import com.atguigu.atcrowdfunding.vo.Data;
@Controller
@RequestMapping("/role")
public class RoleController {
	
		@Autowired
		private RoleService roleService;
		
		@Autowired
		private PermissionService permissionService;
		
		
		 //修改
		  @RequestMapping("/edit")
			public String toedit(Integer id,Map map){		
			  	Role role = roleService.getRole(id);
			  	map.put("role",role);
				return "role/edit";
			}
		  //修改
		  @ResponseBody
		  @RequestMapping("/doEdit")
		  public  Object doEdit(Role role) {
			  AjaxResult result = new AjaxResult(); 
			  try { 
				  int count = roleService.updateByPrimaryKey(role);
				  result.setSuccess(count==1);
				  
		  } catch (Exception e) {
			  result.setSuccess(false);
			  e.printStackTrace();
			  result.setMessage("数据修改失败"); 
		  } 
			  	return result ; //将对象序列化为JSON 的字符串，以流的形式返回 
		  }
	//角色维护
		@RequestMapping("/index")
		public String doRole() {
			return "role/index";
		} 
		
		@ResponseBody
		@RequestMapping("/pageQuery")
		public Object pageQuery(String queryText,@RequestParam(required = false, defaultValue = "1") Integer pageno,
				@RequestParam(required = false, defaultValue = "3") Integer pagesize){
			AjaxResult result = new AjaxResult();
			try {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("pageno", pageno); // 空指针异常
				paramMap.put("pagesize", pagesize);
				if(StringUtil.isNotEmpty(queryText)){
					queryText = queryText.replaceAll("%", "\\\\%"); //斜线本身需要转译
				}
				
				paramMap.put("queryText", queryText);
				
				// 分页查询数据
				Page<Role> rolePage = roleService.queryRole(paramMap);
				
				
				result.setPage(rolePage);
				result.setSuccess(true);
			} catch (Exception e) {			
				e.printStackTrace();
				result.setSuccess(false);
			}
			return  result;
		}
		
		//权限分配

		@RequestMapping("/assignPermission")
		public String assignPermission() {
			return "role/assignPermission";
		}
		
		@ResponseBody
		@RequestMapping("/loadDataAsync")
		public Object loadDataAsync(Integer roleid){
			
			List<Permission> root = new ArrayList<Permission>();
			
			List<Permission> childredPermissons =  permissionService.queryAllPermission();
			
			
			//根据角色id查询该角色之前所分配过的许可.
			List<Integer> permissonIdsForRoleid = permissionService.queryPermissionidsByRoleid(roleid);
			
			
			Map<Integer,Permission> map = new HashMap<Integer,Permission>();//100
			
			for (Permission innerpermission : childredPermissons) {
				map.put(innerpermission.getId(), innerpermission);
				if(permissonIdsForRoleid.contains(innerpermission.getId())){
					innerpermission.setChecked(true);
				}
			}
			for (Permission permission : childredPermissons) { //100
				//通过子查找父
				//子菜单
				Permission child = permission ; //假设为子菜单
				if(child.getPid() == null ){
					root.add(permission);
				}else{
					//父节点
					Permission parent = map.get(child.getPid());
					parent.getChildren().add(child);
				}
			}
			return root ;
		}
		
		@ResponseBody
		@RequestMapping("/doAssignPermission")
		public Object doAssignPermission(Integer roleid, Data datas){
			AjaxResult result = new AjaxResult();
			try {
				int count = roleService.saveRolePermissionRelationship(roleid,datas);
				
				result.setSuccess(count==datas.getIds().size());
				
			} catch (Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
			}
			
			return result;
		}
		
		
		//删除角色
		@ResponseBody
		@RequestMapping("/delete")
		public Object delete(Integer uid) {
			AjaxResult result = new AjaxResult();
			 try { 
				  int count = roleService.deleteByPrimaryKey(uid);
				  result.setSuccess(count==1);
				  
		  } catch (Exception e) {
			  result.setSuccess(false);
			  e.printStackTrace();
			  result.setMessage("数据删除失败"); 
		  } 
			return result;
		}
		
		//批量删除
		@ResponseBody
		@RequestMapping("/batchDelete")
		public Object batchDelete(Data data) {
			AjaxResult result = new AjaxResult();
			 try { 
				  int count = roleService.deleteByPrimaryKeys(data);
				  result.setSuccess(count==data.getDatas().size());
				  
		  } catch (Exception e) {
			  result.setSuccess(false);
			  e.printStackTrace();
			  result.setMessage("数据删除失败"); 
		  } 
			return result;
		}
		
		//新增
		@RequestMapping("/add")
		public String add() {
			return "role/add";
		}
		//新增
		@ResponseBody
		@RequestMapping("/doAdd")
		public Object doAdd(Role record) {
			AjaxResult result = new AjaxResult();
			 try { 
				  int count = roleService.saveRole(record);
				  result.setSuccess(count==1);
				  
		  } catch (Exception e) {
			  result.setSuccess(false);
			  e.printStackTrace();
			  result.setMessage("用户增加失败"); 
		  } 
			return result;
		}
		
		
		
	}
