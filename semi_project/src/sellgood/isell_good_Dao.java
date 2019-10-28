package sellgood;

import java.util.List;

public interface isell_good_Dao {
	
	public void like(sellgoodDto dto);
	
	public boolean pickId(int seq, String id);

	public boolean pickDeleteId(int seq, String id);
}
