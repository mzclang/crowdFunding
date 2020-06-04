package com.atguigu.atcrowdfunding.manager.service;

import java.util.Map;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

public interface AdvertService {

	//查询
	Page<Advertisement> pageQuery(Map<String, Object> advertMap);

	//增加
	int insertAdvert(Advertisement advert);

	//删除
	int deleteAdvert(Integer id);

	//批量删除
	int deleteAdverts(Data ds);

	Advertisement getAdvertId(Integer id);

	int updateAdvert(Advertisement advert);

}
