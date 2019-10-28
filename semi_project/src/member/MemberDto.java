package member;

import java.io.Serializable;

public class MemberDto implements Serializable {
	private String id;
	private String pwd;
	private String nickname;
	private String name;
	private int postnum;
	private String address;
	private String address_detail;
	private String email;
	private int point;
	private String jdate;
	private int auth;
	
	public MemberDto() {
	}

	public MemberDto(String id, String pwd, String nickname, String name, int postnum, String address,
			String address_detail, String email, int point, String jdate, int auth) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.nickname = nickname;
		this.name = name;
		this.postnum = postnum;
		this.address = address;
		this.address_detail = address_detail;
		this.email = email;
		this.point = point;
		this.jdate = jdate;
		this.auth = auth;
	}

	public MemberDto(String id, String pwd, String nickname) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.nickname = nickname;
	}

	public MemberDto(String id, String pwd, String nickname, String name, int postnum, String address,
			String address_detail, String email) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.nickname = nickname;
		this.name = name;
		this.postnum = postnum;
		this.address = address;
		this.address_detail = address_detail;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPostnum() {
		return postnum;
	}

	public void setPostnum(int postnum) {
		this.postnum = postnum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress_detail() {
		return address_detail;
	}

	public void setAddress_detail(String address_detail) {
		this.address_detail = address_detail;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getJdate() {
		return jdate;
	}

	public void setJdate(String jdate) {
		this.jdate = jdate;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", pwd=" + pwd + ", nickname=" + nickname + ", name=" + name + ", postnum="
				+ postnum + ", address=" + address + ", address_detail=" + address_detail + ", email=" + email
				+ ", point=" + point + ", jdate=" + jdate + ", auth=" + auth + "]";
	}

	
	
}
