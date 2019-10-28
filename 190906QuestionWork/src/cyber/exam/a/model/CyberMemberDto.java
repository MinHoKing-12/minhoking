package cyber.exam.a.model;

import java.io.Serializable;
/*
DROP TABLE EXAM_MEMBER
CASCADE CONSTRAINTS;

CREATE TABLE EXAM_MEMBER(
	ID VARCHAR2(50) PRIMARY KEY,
	PW VARCHAR2(50) NOT NULL,
	NAME VARCHAR2(100) NOT NULL,
	AUTH NUMBER NOT NULL
);
*/
public class CyberMemberDto implements Serializable{

	private String id;
	private String pw;
	private String name;
	private int auth;
	
	public CyberMemberDto() {
	}

	public CyberMemberDto(String id, String pw, String name, int auth) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.auth = auth;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "CyberMemberDto [id=" + id + ", pw=" + pw + ", name=" + name + ", auth=" + auth + "]";
	}
	
}
