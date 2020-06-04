package com.atguigu.atcrowdfunding.manager.service;

import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

public interface CertService {


	Page<Cert> queryPage(Map<String, Object> paramMap);

	int Addcert(Cert cert);

	int delete(Integer id);

	int deletes(Data data);

	int updateByPrimaryKey(Cert cert);

	Cert getCertId(Integer id);

	List<Cert> queryllCert();

	List<Cert> queryCertByAccttype(String accttype);

	void saveMemberCert(List<MemberCert> certimgs);

	

}
