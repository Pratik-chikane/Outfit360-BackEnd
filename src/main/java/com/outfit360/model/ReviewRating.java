package com.outfit360.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ReviewRating {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String review;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "rating")
	private double rating;

	private LocalDateTime createdAt;

	public ReviewRating(Long id, String review, User user, Product product, double rating, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.review = review;
		this.user = user;
		this.product = product;
		this.rating = rating;
		this.createdAt = createdAt;
	}

	public ReviewRating() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	
}
