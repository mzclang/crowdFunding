package com.atguigu.atcrowdfunding.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.util.AjaxResult;

@Controller
@RequestMapping("/permission")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;
	
	//许可维护
	@RequestMapping("/index")
	public String dopermission() {
		return "permission/index";
	}
	

	//Demo5 - 用Map集合来查找父,来组合父子关系.减少循环的次数 ,提高性能.
		@ResponseBody
		@RequestMapping("/loadData")
		public Object loadData(){
			AjaxResult result = new AjaxResult();
			try {
				
				List<Permission> root = new ArrayList<Permission>();

				List<Permission> childredPermissons =  permissionService.queryAllPermission();
				
				Map<Integer,Permission> map = new HashMap<Integer,Permission>();//100
				
				for (Permission innerpermission : childredPermissons) {
					map.put(innerpermission.getId(), innerpermission);
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
				result.setSuccess(true);
				result.setData(root);
				
			} catch (Exception e) {
				result.setSuccess(false);
				result.setMessage("加载许可树数据失败!");
			}
			

			return result ;
		}
		
		//删除
		@ResponseBody
		@RequestMapping("/delete")
		public Object deletePermission(Integer id,HttpServletResponse response) {
			AjaxResult result = new AjaxResult();
			try {
				int count = permissionService.deletePermission(id);
				 result.setSuccess(count==1);
			} catch (Exception e) {
				result.setSuccess(false);
				 e.printStackTrace();
				result.setMessage("删除数据失败");
			}
			return result;
		}
		
		//增加toAdd
		@RequestMapping("/toAdd")
		public String toAdd() {
			return "permission/add";
		}
		
		@ResponseBody
		@RequestMapping("/doAdd")
		public Object doAdd(Permission per) {
			AjaxResult result = new AjaxResult();
			
			try {
				int  count = permissionService.addpermission(per);
				result.setSuccess(count==1);
			} catch (Exception e) {
				result.setSuccess(false);
				e.printStackTrace();
				result.setMessage("数据增加失败");
			}
			
			return result;
		}
		
		
		
		//修改toUpdate
		@RequestMapping("/toUpdate")
		public String toUpdate(Integer id,Map map) {
			Permission permission =  permissionService.getPermission(id);
			map.put("permission",permission);
			return "permission/update";
		}
		
		//修改doUpdate
		@ResponseBody
		@RequestMapping("/doUpdate")
		public Object doUpdate(Permission per) {
			AjaxResult result = new AjaxResult();
			try {
				int count =permissionService.update(per);
				result.setSuccess(count==1);
			} catch (Exception e) {
				result.setSuccess(false);
				 e.printStackTrace();
				 result.setMessage("修改失败");
			}
			return result;
		}
}
