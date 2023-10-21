package com.outfit360.request;

public class ReviewRequest {

	private Long productId;
	private String review;
	private Double rating;

	public ReviewRequest() {
	}

	public ReviewRequest(Long productId, String review, Double rating) {
		super();
		this.productId = productId;
		this.review = review;
		this.rating = rating;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	}
