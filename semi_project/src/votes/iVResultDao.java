package votes;

import java.util.List;

import votes.VResultDto;

public interface iVResultDao {
	
	 public boolean Pickplayer(VResultDto dto);
	 public boolean votenick(String id);
	 public int getAllpick(int searchnum);
	 public List<VResultDto> VotepagingList(int page, int searchnum);
	 public int allwinner(int playnum);
	 public VResultDto FindWinner(int Rnum, int playnum);
	 public boolean winmessage(String to, String from,String content);

}
