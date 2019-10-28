package sellbbs;

public class sell_Dto {

	private int seq;			// 고유번호
	private String id;			// 아이디
	private String title;		// 제목
	private String content;		// 내용
	private int rcount;			// 조회수
	private int acount;			// 관심수
	private String wdate;		// 작성일
	private int del;			// 삭제번호(0,1)
	private int price;			// 가격
	private String category;	// 삽니다, 팝니다 카테고리
	private String pcategory;	// 의류, 장비, 사인, 티켓, 기타
	private int complete;		// 노말(0), 거래완료(1)
	private int report;			// 신고수
	private String filename;	// 파일이름
	private String nickname;	// 닉네임
	
	public sell_Dto() {
		// TODO Auto-generated constructor stub
	}

	public int getSeq() {
		return seq;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRcount() {
		return rcount;
	}

	public void setRcount(int rcount) {
		this.rcount = rcount;
	}

	public int getAcount() {
		return acount;
	}

	public void setAcount(int acount) {
		this.acount = acount;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPcategory() {
		return pcategory;
	}

	public void setPcategory(String pcategory) {
		this.pcategory = pcategory;
	}

	public int getComplete() {
		return complete;
	}

	public void setComplete(int complete) {
		this.complete = complete;
	}

	public int getReport() {
		return report;
	}

	public void setReport(int report) {
		this.report = report;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "sell_Dto [seq=" + seq + ", nickname=" + nickname + ", title=" + title + ", content=" + content
				+ ", rcount=" + rcount + ", acount=" + acount + ", wdate=" + wdate + ", del=" + del + ", category="
				+ category + ", pcategory=" + pcategory + ", complete=" + complete + ", report=" + report + "]";
	}
	
	// 전체 생성자
	public sell_Dto(int seq, String id, String title, String content, int rcount, int acount, String wdate,
			int del, int price, String category, String pcategory, int complete, int report, String filename, String nickname) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.rcount = rcount;
		this.acount = acount;
		this.wdate = wdate;
		this.del = del;
		this.price = price;
		this.category = category;
		this.pcategory = pcategory;
		this.complete = complete;
		this.report = report;
		this.filename = filename;
		this.nickname = nickname;
	}
	
	// 게시판에서 보여질 것만 만드는 생성자(번호, 제목, 내용, 시간, 조회수, 관심수, 가격, 카테고리, 상품 카테고리, 파일이름)
	public sell_Dto(int seq, String nickname, String title, String content, String date, String category, String pcategory, int rcount, int acount, int price, String filename) {
		super();
		this.seq = seq;
		this.nickname = nickname;
		this.title = title;
		this.content = content;
		this.wdate = date;
		this.category = category;
		this.pcategory = pcategory;
		this.rcount = rcount;
		this.acount = acount;
		this.price = price;
		this.filename = filename;
	}
	// 파일 등록하는데 필요한 생성자
	public sell_Dto(String nickname, String title, String content, int price, String category, String pcategory,
			String filename) {
		super();
		this.nickname = nickname;
		this.title = title;
		this.content = content;
		this.price = price;
		this.category = category;
		this.pcategory = pcategory;
		this.filename = filename;
	}

	public sell_Dto(int seq) {
		super();
		this.seq = seq;
	}
	
	
	
	
	
}
