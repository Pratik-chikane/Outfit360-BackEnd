package com.outfit360.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.outfit360.exception.ProductException;
import com.outfit360.model.Product;
import com.outfit360.request.CreateProductRequest;

public interface IProductService {

	public Product createProduct(CreateProductRequest req);

	public void deleteProduct(Long productId) throws ProductException;

	public Product updateProduct(Long productId, Product req) throws ProductException;

	public Product findProductById(Long productId) throws ProductException;

	public List<Product> findProductByCategory(String category);

	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

	public List<Product> findAllProduct();

}
