package com.atguigu.atcrowdfunding.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.dao.CertMapper;
import com.atguigu.atcrowdfunding.manager.service.CertService;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

@Service
public class CertServiceImpl implements CertService {

	
	@Autowired
	private CertMapper certMapper;
	
	
	public Page<Cert> queryPage(Map<String, Object> paramMap) {
		Page<Cert> certPage = new Page<Cert>((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
		paramMap.put("startIndex", certPage.getStartIndex());
		List<Cert> certs = certMapper.pageQuery(paramMap);
		// 获取数据的总条数
		int count = certMapper.queryCount(paramMap);
		certPage.setDatas(certs);
		certPage.setTotalsize(count);
		return certPage;
	}

	//增加
	@Override
	public int Addcert(Cert cert) {
		
		
		return certMapper.insert(cert);
	}

	//删除
	@Override
	public int delete(Integer id) {
		return certMapper.deleteByPrimaryKey(id);
	}

	//批量删除
	@Override
	public int deletes(Data ds) {
		return certMapper.deletes(ds);
	}

	@Override
	public int updateByPrimaryKey(Cert cert) {
		return certMapper.updateByPrimaryKey(cert);
	}

	//修改
	@Override
	public Cert getCertId(Integer id) {
		return certMapper.selectByPrimaryKey(id);
	}

	//分类管理
	@Override
	public List<Cert> queryllCert() {
		return certMapper.selectAll();
	}

	public List<Cert> queryCertByAccttype(String accttype) {
		return certMapper.queryCertByAccttype(accttype);
	}

	@Override
	public void saveMemberCert(List<MemberCert> certimgs) {
		for (MemberCert memberCert : certimgs) {
			certMapper.insertMemberCert(memberCert);
		}
	}


	
	

}
