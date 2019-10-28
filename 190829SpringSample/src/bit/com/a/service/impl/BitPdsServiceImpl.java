package bit.com.a.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bit.com.a.dao.BitPdsDao;
import bit.com.a.model.BbsParam;
import bit.com.a.model.PdsDto;
import bit.com.a.service.BitPdsService;

@Service
public class BitPdsServiceImpl implements BitPdsService {

	@Autowired
	BitPdsDao bitpdsdao;


	@Override
	public boolean uploadPds(PdsDto dto) {
		return bitpdsdao.uploadPds(dto);
	}

	@Override
	public boolean PdsDelete(int seq) {
		return bitpdsdao.PdsDelete(seq);
	}

	@Override
	public boolean PdsReadCount(int seq) {
		return bitpdsdao.PdsReadCount(seq);
	}

	@Override
	public boolean PdsDownCount(int seq) {
		return bitpdsdao.PdsDownCount(seq);
	}

	@Override
	public PdsDto PdsDetail(int seq) {
		return bitpdsdao.PdsDetail(seq);
	}

	@Override
	public boolean PdsUpdate(PdsDto dto) {
		return bitpdsdao.PdsUpdate(dto);
	}

	@Override
	public PdsDto getOldfileName(int seq) {
		return bitpdsdao.getOldfileName(seq);
	}

	@Override
	public List<PdsDto> getPdsList(BbsParam param) {
		return bitpdsdao.getPdsList(param);
	}

	@Override
	public int getPdsCount(BbsParam param) {
		return bitpdsdao.getPdsCount(param);
	}


}
