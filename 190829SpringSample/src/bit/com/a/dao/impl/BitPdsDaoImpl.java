package bit.com.a.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bit.com.a.dao.BitPdsDao;
import bit.com.a.model.BbsParam;
import bit.com.a.model.PdsDto;

@Repository
public class BitPdsDaoImpl implements BitPdsDao {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	String ns = "Pds.";

	@Override
	public List<PdsDto> getPdsList(BbsParam param) {
		return sqlSession.selectList(ns + "getPdsList", param);
	}

	@Override
	public boolean uploadPds(PdsDto dto) {
		
		int n = sqlSession.insert(ns + "uploadPds", dto);
		
		return n>0?true:false;
	}

	@Override
	public boolean PdsDelete(int seq) {
		
		int n = sqlSession.delete(ns + "PdsDelete", seq);
		
		return n>0?true:false;
	}

	@Override
	public boolean PdsReadCount(int seq) {
		int n = sqlSession.update(ns + "PdsReadCount", seq);
		return n>0?true:false;
	}

	@Override
	public boolean PdsDownCount(int seq) {
		int n = sqlSession.update(ns + "PdsDownCount", seq);
		return n>0?true:false;
	}

	@Override
	public PdsDto PdsDetail(int seq) {
		PdsDto dto = sqlSession.selectOne(ns + "PdsDetail", seq);
		return dto;
	}

	@Override
	public boolean PdsUpdate(PdsDto dto) {
		int n = sqlSession.update(ns + "PdsUpdate", dto);
		return n>0?true:false;
	}

	@Override
	public PdsDto getOldfileName(int seq) {

		PdsDto dto = sqlSession.selectOne(ns + "OldFilename", seq);
		return dto;
	}

	@Override
	public int getPdsCount(BbsParam param) {
		return sqlSession.selectOne(ns + "getPdsCount", param);
	}
	
	
}
