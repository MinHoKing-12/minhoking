package cyber.exam.a.model;

import java.io.Serializable;
import java.util.List;



public class ReplyBean implements Serializable{

	public ReplyBean() {
	}
	
	
	
	public ReplyBean(List<ExamReplyDto> replylist) {
		super();
		this.replylist = replylist;
	}



	private List<ExamReplyDto> replylist;

	public List<ExamReplyDto> getReplylist() {
		return replylist;
	}

	public void setReplylist(List<ExamReplyDto> replylist) {
		this.replylist = replylist;
	}



	@Override
	public String toString() {
		return "ReplyBean [replylist=" + replylist + "]";
	}
	
	

}
