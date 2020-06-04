package com.atguigu.atcrowdfunding.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.RolePermission;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.dao.RoleMapper;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	//角色维护
	@Override
	public Page queryRole(Map<String, Object> paramMap) {
	Page page = new Page((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
		
		Integer startIndex = page.getStartIndex();
		paramMap.put("startIndexRole",startIndex);
		
		List<User> datas = roleMapper.queryRoleList(paramMap);
	
		page.setDatas(datas);
		
		Integer totalsize =  roleMapper.queryRoleCount(paramMap);
		
		page.setTotalsize(totalsize);
		return page;
	}
	//删除
	@Override
	public int deleteByPrimaryKey(Integer id) {
		
		return roleMapper.deleteByPrimaryKey(id);
	}
	
	//批量删除
	@Override
	public int deleteByPrimaryKeys(Data data) {
		
		return roleMapper.deleteByPrimaryKeys(data);
	}
	
	//新增
	@Override
	public int saveRole(Role record) {
		
		return roleMapper.insert(record);
	}
	
	//修改
	@Override
	public Role getRole(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}
	//修改
	@Override
	public int updateByPrimaryKey(Role record) {
		return roleMapper.updateByPrimaryKey(record);
	}
	//分配权限
	@Override
	public int saveRolePermissionRelationship(Integer roleid, Data datas) {
		roleMapper.deleteRolePermissionRelationship(roleid);
		int totalCount = 0 ;
		List<Integer> ids = datas.getIds();
		for (Integer permissionid : ids) {
			RolePermission rp = new RolePermission();
			rp.setRoleid(roleid);
			rp.setPermissionid(permissionid);
			int count = roleMapper.insertRolePermission(rp);
			totalCount += count ;
		}
		
		return totalCount;
	}

}
