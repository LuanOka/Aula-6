package br.usjt.ads.arqdes.model.javabeans;

public class Elenco {
	private int cast_id;
	private String character;
	private String credit_id;
	private int gender;
	private int id;
	private String name;
	private int order;
	private String profile_path;
	public int getCast_id() {
		return cast_id;
	}
	public void setCast_id(int cast_id) {
		this.cast_id = cast_id;
	}
	public String getCharacter() {
		return character;
	}
	public void setCharacter(String character) {
		this.character = character;
	}
	public String getCredit_id() {
		return credit_id;
	}
	public void setCredit_id(String credit_id) {
		this.credit_id = credit_id;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getProfile_path() {
		return profile_path;
	}
	public void setProfile_path(String profile_path) {
		this.profile_path = profile_path;
	}
	@Override
	public String toString() {
		return "Elenco [cast_id=" + cast_id + ", character=" + character + ", credit_id=" + credit_id + ", gender="
				+ gender + ", id=" + id + ", name=" + name + ", order=" + order + ", profile_path=" + profile_path
				+ "]";
	}
}
