package bit.com.a.service;

import java.util.List;

import bit.com.a.model.BbsParam;
import bit.com.a.model.BbslistDto;

public interface BitBbslistServiece {

	
	public List<BbslistDto> getBbsList(BbsParam param) throws Exception;
	
	public int getBbsCount(BbsParam param) throws Exception;
	
	public boolean BbsWrite(BbslistDto dto) throws Exception;
	
	public BbslistDto bbsDetail(int seq) throws Exception;
	
	public boolean bbsUpdate(BbslistDto dto) throws Exception;
	
	public boolean bbsDelete(int seq) throws Exception;
	
	public boolean bbsReadCount(int seq) throws Exception;
	
	public boolean answerWrite(BbslistDto dto) throws Exception;
}
