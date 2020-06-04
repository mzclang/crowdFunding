package com.atguigu.atcrowdfunding.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.manager.dao.AccountTypeCertMapper;
import com.atguigu.atcrowdfunding.manager.service.CerttypeService;

@Service
public class CerttypeServiceImpl implements CerttypeService {
	@Autowired
	private AccountTypeCertMapper accountTypeCertMapper;

	@Override
	public List<Map<String, Object>> queryCertAccttype() {
		return accountTypeCertMapper.queryCertAccttype();
	}

	@Override
	public int insertAcctTypeCert(Map<String, Object> paramMap) {
		return accountTypeCertMapper.insertAcctTypeCert(paramMap);
	}

	@Override
	public int deleteAcctTypeCert(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return accountTypeCertMapper.deleteAcctTypeCert(paramMap);
	}
}
