package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface CertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cert record);

    Cert selectByPrimaryKey(Integer id);

    List<Cert> selectAll();

    int updateByPrimaryKey(Cert record);
    

	Integer queryCount(Map<String, Object> paramMap);

	List<Cert> pageQuery(Map<String, Object> paramMap);


	int deletes(Data ds);

	List<Cert> queryCertByAccttype(String accttype);

	void insertMemberCert(MemberCert memberCert);

}