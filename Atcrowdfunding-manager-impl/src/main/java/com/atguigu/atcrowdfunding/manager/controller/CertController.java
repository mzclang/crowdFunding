package com.atguigu.atcrowdfunding.manager.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.CertService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.StringUtil;
import com.atguigu.atcrowdfunding.vo.Data;

@Controller
@RequestMapping("/cert")
public class CertController {
	
	@Autowired
	private CertService certService;
	
	//业务管理
	@RequestMapping("/index")
	public String index() {
		return "cert/index";
	}
	
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String pagetext, Integer pageno, Integer pagesize) {
		AjaxResult result = new AjaxResult();
		
		try {
			// 查询资质数据
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pageno", pageno);
			paramMap.put("pagesize", pagesize);
			if ( StringUtil.isNotEmpty(pagetext) ) {
				//pagetext = pagetext.replaceAll("%", "\\\\%");
			}
			paramMap.put("pagetext", pagetext);
			
			// 分页查询数据
			Page<Cert> page = certService.queryPage(paramMap);

			result.setPage(page);
			result.setSuccess(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	//新增
	@RequestMapping("/add")
	public String Add() {
		return "cert/add";
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(Cert cert) {
		AjaxResult result = new AjaxResult();
		
		try {
			int count = certService.Addcert(cert);
			result.setSuccess(count==1);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("增加数据失败");
		}
		return result;
	}
	
	
	//删除
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		AjaxResult result = new AjaxResult();
		
		try {
			int count = certService.delete(id);
			result.setSuccess(count==1);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("删除数据失败");
		}
		
		return result;
				
	}
	
	//批量删除
	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes(Data data) {
		AjaxResult result = new AjaxResult();
		
		try {
			int count = certService.deletes(data);
			result.setSuccess(count==data.getDatas().size());
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("批量删除数据失败");
		}
		return result;
	}
	
	//修改
	@RequestMapping("/edit")
	public String edit(Integer id,Map map) {
		Cert cert = certService.getCertId(id);
	  	map.put("cert",cert);
		return "cert/edit";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Cert cert) {
		AjaxResult result = new AjaxResult();
		try { 
			  int count = certService.updateByPrimaryKey(cert);
			  result.setSuccess(count==1);
			  
	  } catch (Exception e) {
		  result.setSuccess(false);
		  e.printStackTrace();
		  result.setMessage("数据修改失败"); 
	  } 
		return result;
	}
}
