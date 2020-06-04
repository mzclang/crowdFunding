package com.atguigu.atcrowdfunding.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.manager.dao.AdvertisementMapper;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

@Service
public class AdvertServiceImpl implements AdvertService {

	@Autowired
	private AdvertisementMapper advertMapper;
	//查询数据
	@Override
	public Page<Advertisement> pageQuery(Map<String, Object> advertMap) {
		
		Page<Advertisement> advertPage = new Page<Advertisement>((Integer)advertMap.get("pageno"),(Integer)advertMap.get("pagesize"));
		advertMap.put("startIndex",advertPage.getStartIndex());
		List<Advertisement> advertList = advertMapper.pageQuery(advertMap);
		
		//获取数据的总条数
		int count = advertMapper.queryCount(advertMap);
		
		advertPage.setDatas(advertList);
		advertPage.setTotalsize(count);
		
		return advertPage;
	}
	
	//增加
	@Override
	public int insertAdvert(Advertisement advert) {
		return advertMapper.insertAdvert(advert);
	}

	//删除
	@Override
	public int deleteAdvert(Integer id) {
		return advertMapper.deleteAdvert(id);
	}

	//批量删除
	@Override
	public int deleteAdverts(Data ds) {
		
		return advertMapper.deleteAdverts(ds);
	}

	//修改
	@Override
	public Advertisement getAdvertId(Integer id) {
		return advertMapper.selectByPrimaryKey(id);
	}

	//修改数据
	@Override
	public int updateAdvert(Advertisement advert) {
		return advertMapper.updateByPrimaryKey(advert);
	}

}
