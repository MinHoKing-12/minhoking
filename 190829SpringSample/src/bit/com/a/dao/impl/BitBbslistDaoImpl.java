package bit.com.a.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bit.com.a.dao.BitBbslistDao;
import bit.com.a.model.BbsParam;
import bit.com.a.model.BbslistDto;

@Repository
public class BitBbslistDaoImpl implements BitBbslistDao {

	// 어플리케이션에 있는 세션을 사용
	@Autowired
	SqlSession sqlSession;
	
	String namespace = "bbslist.";

	@Override
	public List<BbslistDto> getBbsList(BbsParam param) throws Exception {

		List<BbslistDto> list = sqlSession.selectList(namespace + "allbbslist", param);
		
		return list;
	}

	@Override
	public int getBbsCount(BbsParam param) throws Exception {
		return sqlSession.selectOne(namespace + "getBbsCount", param);
	}

	@Override
	public boolean BbsWrite(BbslistDto dto) throws Exception {
		
		int n = sqlSession.insert(namespace + "BbsWrite", dto);
		
		return n>0?true:false;
	}

	@Override
	public BbslistDto bbsDetail(int seq) throws Exception {
		
		BbslistDto dto = sqlSession.selectOne(namespace + "BbsDetail", seq);
		
		return dto;
	}

	@Override
	public boolean bbsUpdate(BbslistDto dto) throws Exception {
		
		int n = sqlSession.update(namespace + "BbsUpdate", dto);
		return n>0?true:false;
	}

	@Override
	public boolean bbsDelete(int seq) throws Exception {

		int n = sqlSession.update(namespace + "BbsDelete", seq);
		return n>0?true:false;
	}

	@Override
	public boolean bbsReadCount(int seq) throws Exception {
		int n = sqlSession.update(namespace + "BbsReadCount", seq);
		return n>0?true:false;
	}

	@Override
	public boolean answerWrite(BbslistDto dto) throws Exception {
			
			int n1 = sqlSession.update(namespace + "aupdate", dto);
			int n2 = sqlSession.insert(namespace + "ainsert", dto);
			
			boolean check = false;
			if(n1 > 0 && n2 >0) {
				check = true;
			}else {
				check = false;
			}
			return check;
		
	}
}
