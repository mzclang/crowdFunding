package com.atguigu.atcrowdfunding.manager.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.StringUtil;
import com.atguigu.atcrowdfunding.vo.Data;


@Controller
@RequestMapping("/advert")
public class AdvertController {
	
	@Autowired
	private AdvertService advertService;
	
	//广告维护
	@RequestMapping("/index")
	public String index() {
		return "advert/index";
	}
	
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String pagetext, Integer pageno, Integer pagesize) {
		AjaxResult result = new AjaxResult();
		try {
			Map<String,Object> advertMap = new HashMap<String,Object>();
			advertMap.put("pageno",pageno);
			advertMap.put("pagesize",pagesize);
			if(StringUtil.isNotEmpty(pagetext)) {
				pagetext = pagetext.replace("%","////%");
			}
			advertMap.put("pagetext",pagetext);
			// 分页查询
			Page<Advertisement> page = advertService.pageQuery(advertMap);
			result.setPage(page);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("查询数据失败");
			
		}
		return result;
	}
	//增加
	@RequestMapping("/add")
	public String Add() {
		return "advert/add";
	}
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(HttpServletRequest request, Advertisement advert ,HttpSession session) {
		AjaxResult result = new AjaxResult();
		
		try {
			MultipartHttpServletRequest mrep = (MultipartHttpServletRequest) request;
			
			MultipartFile mfile = mrep.getFile("advpic");//获取前端图片文件
			
			String name = mfile.getOriginalFilename();//java.jpg
			
			String extname = name.substring(name.lastIndexOf("."));//.jpg
			
			String iconpath = UUID.randomUUID().toString()+extname; //232243343.jpg
			
			ServletContext servletContext = session.getServletContext();
			
			String realpath = servletContext.getRealPath("/pics");
			
			String path = realpath+"\\adv\\"+iconpath;
			
			mfile.transferTo(new File(path));
			
			User user = (User) session.getAttribute(Const.LOGIN_USER);
			advert.setUserid(user.getId());
			advert.setStatus("1");
			advert.setIconpath(iconpath);
			
			int count = advertService.insertAdvert(advert);
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
			int  count = advertService.deleteAdvert(id);
			if(count==1) {
				result.setSuccess(true);
			}else {
				result.setSuccess(false);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}
	
	//批量删除
	@ResponseBody
	@RequestMapping("/batchDelete")
	public Object batchDelete(Data ds) {
		
		AjaxResult result = new AjaxResult();
		
		
		try {
			int count = advertService.deleteAdverts(ds);
			if (count==ds.getDatas().size()) {
				result.setSuccess(true);
			}else {
				result.setSuccess(false);
				result.setMessage("批量删除数据失败");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}
	
	//修改
	@RequestMapping("/edit")
	public String edit(Integer id, Map map) {
		Advertisement advert = advertService.getAdvertId(id);
		map.put("advert", advert);
		return "advert/edit";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object Update(Advertisement advert) {
		AjaxResult result = new AjaxResult();
		
		try {
			int count = advertService.updateAdvert(advert);
			System.out.println("ascdsafeferqgferqgerqg:------"+count);
			if ( count == 1 ) {
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}
	
}
