package bit.com.a.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bit.com.a.dao.BitBbslistDao;
import bit.com.a.model.BbsParam;
import bit.com.a.model.BbslistDto;
import bit.com.a.service.BitBbslistServiece;

@Service
public class BitBbslistServiceImpl implements BitBbslistServiece {

	@Autowired
	private BitBbslistDao BitBbslistDao;


	public List<BbslistDto> getBbsList(BbsParam param) throws Exception {
		return BitBbslistDao.getBbsList(param);
	}
	
	@Override
	public int getBbsCount(BbsParam param) throws Exception {
		return BitBbslistDao.getBbsCount(param);
	}
	
	@Override
	public boolean BbsWrite(BbslistDto dto) throws Exception {
		return BitBbslistDao.BbsWrite(dto);
	}

	@Override
	public BbslistDto bbsDetail(int seq) throws Exception {
		return BitBbslistDao.bbsDetail(seq);
	}

	@Override
	public boolean bbsUpdate(BbslistDto dto) throws Exception {
		return BitBbslistDao.bbsUpdate(dto);
	}

	@Override
	public boolean bbsDelete(int seq) throws Exception {
		return BitBbslistDao.bbsDelete(seq);
	}

	@Override
	public boolean bbsReadCount(int seq) throws Exception {
		return BitBbslistDao.bbsReadCount(seq);
	}

	@Override
	public boolean answerWrite(BbslistDto dto) throws Exception {
		return BitBbslistDao.answerWrite(dto);
	}
}
