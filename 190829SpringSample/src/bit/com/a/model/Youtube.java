package bit.com.a.model;

import java.io.Serializable;

// 
public class Youtube implements Serializable {
	
	private String title;
	private String url;			// 주소
	private String img;			

	public Youtube() {
		// TODO Auto-generated constructor stub
	}

	public Youtube(String title, String url, String img) {
		super();
		this.title = title;
		this.url = url;
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "Youtube [title=" + title + ", url=" + url + ", img=" + img + "]";
	}
	
	public String getVname() {
		return toUrl(this.url);
	}
	
	public String toUrl(String msg) {
		String tt = "";
		if(msg.indexOf("=") == 1) {		// 없으면
			tt = msg;
		}else {	// 있으면
			tt = msg.substring(msg.indexOf("=")+1);
		}
		return tt;
	}
	
}
