package com.example.facebook_login.model;


public class Movie{
	private String title, thumbnailUrl, category, description, images;
	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	int checkins;
	private double latitude, longitude;
	
	public Movie() {
	}
	
	public Movie(String title, String thumbnailUrl, String category, String description, String images, int checkins) {
		this.title = title;
		this.thumbnailUrl = thumbnailUrl;
		this.category = category;
		this.description = description;
		this.checkins = checkins;
		this.images = images;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public int getCheckins() {
		return checkins;
	}



	public void setCheckins(int checkins) {
		this.checkins = checkins;
	}

	
	

	public double getLatitude() {
		return latitude;
	}



	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}



	public double getLongitude() {
		return longitude;
	}



	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	
	

	

}
