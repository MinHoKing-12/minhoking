package votes;

import java.io.Serializable;

public class VoteDto implements Serializable{

	private int seq;
	private String name;
	private int height;
	private int weight;	
	private String position;
	private String photoname;	
	private int likes; //좋아요 하기위한 변수


	public VoteDto() {
	

	}



public VoteDto(int seq, String name, int height, int weight, String position, String photoname) {
	super();
	this.seq = seq;
	this.name = name;
	this.height = height;
	this.weight = weight;
	this.position = position;
	this.photoname = photoname;
}



public VoteDto(String name, int height, int weight, String position) {
	super();
	this.name = name;
	this.height = height;
	this.weight = weight;
	this.position = position;
}



public int getSeq() {
	return seq;
}



public void setSeq(int seq) {
	this.seq = seq;
}



public String getName() {
	return name;
}



public void setName(String name) {
	this.name = name;
}



public int getHeight() {
	return height;
}



public void setHeight(int height) {
	this.height = height;
}



public int getWeight() {
	return weight;
}



public void setWeight(int weight) {
	this.weight = weight;
}



public String getPosition() {
	return position;
}



public void setPosition(String position) {
	this.position = position;
}



public String getPhotoname() {
	return photoname;
}



public void setPhotoname(String photoname) {
	this.photoname = photoname;
}



public int getLikes() {
	return likes;
}



public void setLikes(int likes) {
	this.likes = likes;
}



public VoteDto(int seq, String name, int height, int weight, String position, String photoname, int likes) {
	super();
	this.seq = seq;
	this.name = name;
	this.height = height;
	this.weight = weight;
	this.position = position;
	this.photoname = photoname;
	this.likes = likes;
}



@Override
public String toString() {
	return "VoteDto [seq=" + seq + ", name=" + name + ", height=" + height + ", weight=" + weight + ", position="
			+ position + ", photoname=" + photoname + ", likes=" + likes + "]";
}



}