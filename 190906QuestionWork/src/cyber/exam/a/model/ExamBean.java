package cyber.exam.a.model;

import java.io.Serializable;
import java.util.List;




public class ExamBean implements Serializable {

	//// 시간
	private int syear;		// 시작 시간
	private int smonth;
	private int sday;

	
	private int eyear;		// 종료 시간
	private int emonth;
	private int eday;
	
	public ExamBean() {
		// TODO Auto-generated constructor stub
	}

	public int getSyear() {
		return syear;
	}

	public void setSyear(int syear) {
		this.syear = syear;
	}

	public int getSmonth() {
		return smonth;
	}

	public void setSmonth(int smonth) {
		this.smonth = smonth;
	}

	public int getSday() {
		return sday;
	}

	public void setSday(int sday) {
		this.sday = sday;
	}

	public int getEyear() {
		return eyear;
	}

	public void setEyear(int eyear) {
		this.eyear = eyear;
	}

	public int getEmonth() {
		return emonth;
	}

	public void setEmonth(int emonth) {
		this.emonth = emonth;
	}

	public int getEday() {
		return eday;
	}

	public void setEday(int eday) {
		this.eday = eday;
	}

	public ExamBean(int syear, int smonth, int sday, int eyear, int emonth, int eday) {
		super();
		this.syear = syear;
		this.smonth = smonth;
		this.sday = sday;
		this.eyear = eyear;
		this.emonth = emonth;
		this.eday = eday;
	}

	@Override
	public String toString() {
		return "ExamBean [syear=" + syear + ", smonth=" + smonth + ", sday=" + sday + ", eyear=" + eyear + ", emonth="
				+ emonth + ", eday=" + eday + "]";
	}
	
	
	
}
