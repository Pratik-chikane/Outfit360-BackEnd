package com.outfit360.model;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	private String description;

	private int price;

	@Column(name = "discounted_percent")
	private int discountedPercent;

	@Column(name = "discounted_price")
	private int discountedPrice;

	private int quantity;

	private String brand;

	private String color;

	@Embedded
	@ElementCollection
	@Column(name = "sizes")
	private Set<Size> sizes = new HashSet<>();

	@Column(name = "image_url")
	private String imageUrl;

	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewRating> reviewsRatings = new ArrayList<>();

	@Column(name = "num_ratings")
	private int numRatings;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	private LocalDateTime createdAt;
	
	
	public Product() {
		// TODO Auto-generated constructor stub
	}


	


	public Product(Long id, String title, String description, int price, int discountedPercent, int discountedPrice,
			int quantity, String brand, String color, Set<Size> sizes, String imageUrl,
			List<ReviewRating> reviewsRatings, int numRatings, Category category, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.discountedPercent = discountedPercent;
		this.discountedPrice = discountedPrice;
		this.quantity = quantity;
		this.brand = brand;
		this.color = color;
		this.sizes = sizes;
		this.imageUrl = imageUrl;
		this.reviewsRatings = reviewsRatings;
		this.numRatings = numRatings;
		this.category = category;
		this.createdAt = createdAt;
	}





	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getDiscountedPercent() {
		return discountedPercent;
	}


	public void setDiscountedPercent(int discountedPercent) {
		this.discountedPercent = discountedPercent;
	}


	public int getDiscountedPrice() {
		return discountedPrice;
	}


	public void setDiscountedPrice(int discountedPrice) {
		this.discountedPrice = discountedPrice;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public Set<Size> getSizes() {
		return sizes;
	}


	public void setSizes(Set<Size> sizes) {
		this.sizes = sizes;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	


	public List<ReviewRating> getReviews() {
		return reviewsRatings;
	}


	public void setReviews(List<ReviewRating> reviews) {
		this.reviewsRatings = reviews;
	}


	public int getNumRatings() {
		return numRatings;
	}


	public void setNumRatings(int numRatings) {
		this.numRatings = numRatings;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
