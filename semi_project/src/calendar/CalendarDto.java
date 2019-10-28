package calendar;

import java.io.Serializable;

public class CalendarDto implements Serializable {

	private int seq;
	private String team;
	private String stadium;
	private String sche;	
	private String wdate;
	private String outcome;
	private String memo;
	private String imgname;
	private String ocname;
	//private String cancel; 취소여부
	
	public CalendarDto() {
		// TODO Auto-generated constructor stub
	}
	
	public CalendarDto(String team, String stadium, String sche, String wdate , String outcome, String memo) {
		super();
		this.team = team;
		this.stadium = stadium;
		this.sche = sche;
		this.wdate = wdate;
		this.outcome = outcome;
		this.memo = memo;
	}
	
	
	public CalendarDto(int seq, String team, String stadium, String sche, String outcome, String memo) {
		super();
		this.seq = seq;
		this.team = team;
		this.stadium = stadium;
		this.sche = sche;
		this.outcome = outcome;
		this.memo = memo;
	}

	public CalendarDto(int seq, String team, String stadium, String sche, String wdate, String outcome,
			String memo) {
		super();
		this.seq = seq;
		this.team = team;
		this.stadium = stadium;
		this.sche = sche;
		this.wdate = wdate;
		this.outcome = outcome;
		this.memo = memo;
	}

	public CalendarDto(String team, String stadium, String sche, String outcome, String memo) {
		super();
		this.team = team;
		this.stadium = stadium;
		this.sche = sche;
		this.outcome = outcome;
		this.memo = memo;
	}

	public CalendarDto(int seq, String team, String stadium, String sche, String wdate, String outcome,
			String memo, String ocname) {
		super();
		this.seq = seq;
		this.team = team;
		this.stadium = stadium;
		this.sche = sche;
		this.wdate = wdate;
		this.outcome = outcome;
		this.memo = memo;
	}

	public CalendarDto(int seq, String team, String stadium, String sche, String wdate, String outcome,
			String memo, String imgname, String ocname) {
		super();
		this.seq = seq;
		this.team = team;
		this.stadium = stadium;
		this.sche = sche;
		this.wdate = wdate;
		this.outcome = outcome;
		this.memo = memo;
		this.imgname = imgname;
		this.ocname = ocname;
	}
	

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getStadium() {
		return stadium;
	}

	public void setStadium(String stadium) {
		this.stadium = stadium;
	}

	public String getSche() {
		return sche;
	}

	public void setSche(String sche) {
		this.sche = sche;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getImgname() {
		return imgname;
	}

	public void setImgname(String imgname) {
		this.imgname = imgname;
	}

	public String getOcname() {
		return ocname;
	}

	public void setOcname(String ocname) {
		this.ocname = ocname;
	}

	@Override
	public String toString() {
		return "CalendarDto [seq=" + seq + ", team=" + team + ", stadium=" + stadium + ", sche=" + sche
				+ ", wdate=" + wdate + ", outcome=" + outcome + ", memo=" + memo + ", imgname=" + imgname + ", ocname="
				+ ocname + "]";
	}
	
	

	


}


