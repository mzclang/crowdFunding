package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface AdvertisementMapper {
	//Advertisement queryAdvert(Map<String, Object> advertMap);

	List<Advertisement> pageQuery(Map<String, Object> advertMap);

	int queryCount(Map<String, Object> advertMap);

	int insertAdvert(Advertisement advert);

	Advertisement selectByPrimaryKey(Integer id);

	int updateByPrimaryKey(Advertisement advert);

	int deleteAdvert(Integer id);

	int deleteAdverts(Data ds);


}