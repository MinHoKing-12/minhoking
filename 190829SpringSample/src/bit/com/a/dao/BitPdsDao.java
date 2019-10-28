package bit.com.a.dao;

import java.util.List;

import bit.com.a.model.BbsParam;
import bit.com.a.model.PdsDto;

public interface BitPdsDao {
	
	public List<PdsDto> getPdsList(BbsParam param);
	
	public int getPdsCount(BbsParam param);
	
	public boolean uploadPds(PdsDto dto);
	
	public boolean PdsDelete(int seq);
	
	public boolean PdsReadCount(int seq);
	
	public boolean PdsDownCount(int seq);
	
	public PdsDto PdsDetail(int seq);
	
	public boolean PdsUpdate(PdsDto dto);
	
	public PdsDto getOldfileName(int seq);
}
