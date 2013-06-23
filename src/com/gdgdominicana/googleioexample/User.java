package com.gdgdominicana.googleioexample;

import com.fasterxml.jackson.annotation.JsonProperty;


public class User {

	@JsonProperty("created_at")
	private String created_at;
	@JsonProperty("id")
	private long id;
	@JsonProperty("image")
	private String image;
	@JsonProperty("password")
	private String password;
	@JsonProperty("signature")
	private String signature;
	@JsonProperty("updated_at")
	private String updated_at;
	@JsonProperty("username")
	private String username;
	

	@JsonProperty("created_at")
	public String getCreated_at() { return created_at; }

	@JsonProperty("created_at")
	public void setCreated_at(String created_at) { this.created_at = created_at; }

	@JsonProperty("id")
	public long getId() { return id; }

	@JsonProperty("id")
	public void setId(long id) { this.id = id; }

	@JsonProperty("image")
	public String getImage() { return image; }

	@JsonProperty("image")
	public void setImage(String image) { this.image = image; }

	@JsonProperty("password")
	public String getPassword() { return password; }

	@JsonProperty("password")
	public void setPassword(String password) { this.password = password; }

	@JsonProperty("signature")
	public String getSignature() { return signature; }

	@JsonProperty("signature")
	public void setSignature(String signature) { this.signature = signature; }

	@JsonProperty("updated_at")
	public String getUpdated_at() { return updated_at; }

	@JsonProperty("updated_at")
	public void setUpdated_at(String updated_at) { this.updated_at = updated_at; }

	@JsonProperty("username")
	public String getUsername() { return username; }

	@JsonProperty("username")
	public void setUsername(String username) { this.username = username; }

	
	@Override
	public String toString() {
		return this.username;
	}
}