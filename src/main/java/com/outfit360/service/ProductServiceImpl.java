package com.outfit360.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.outfit360.exception.ProductException;
import com.outfit360.model.Category;
import com.outfit360.model.Product;
import com.outfit360.repository.ICategoryRepository;
import com.outfit360.repository.IProductRepository;
import com.outfit360.request.CreateProductRequest;

@Service
public class ProductServiceImpl implements IProductService {

	private IProductRepository productRepo;
	private IUserService userService;
	private ICategoryRepository categoryRepo;

	public ProductServiceImpl(IProductRepository productRepo, IUserService userService,
			ICategoryRepository categoryRepo) {
		this.productRepo = productRepo;
		this.userService = userService;
		this.categoryRepo = categoryRepo;
	}

	@Override
	public Product createProduct(CreateProductRequest req) {
		Category topLevel = categoryRepo.findByName(req.getTopLevelCategory());

		if (topLevel == null) {
			Category topLevelCategory = new Category();
			topLevelCategory.setName(req.getTopLevelCategory());
			topLevelCategory.setLevel(1);

			topLevel = categoryRepo.save(topLevelCategory);
		}
		Category secondLevel = categoryRepo.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());

		if (secondLevel == null) {
			Category secondLevelCategory = new Category();
			secondLevelCategory.setName(req.getSecondLevelCategory());
			secondLevelCategory.setParentCategory(topLevel);
			secondLevelCategory.setLevel(2);

			secondLevel = categoryRepo.save(secondLevelCategory);
		}
		Category thirdLevel = categoryRepo.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());

		if (thirdLevel == null) {
			Category thirdLevelCategory = new Category();
			thirdLevelCategory.setName(req.getThirdLevelCategory());
			thirdLevelCategory.setParentCategory(secondLevel);
			thirdLevelCategory.setLevel(3);

			thirdLevel = categoryRepo.save(thirdLevelCategory);
		}

		Product product = new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountedPercent(req.getDiscountedPercent());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSizes(req.getSize());
		product.setQuantity(req.getQuantity());
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());

		Product savedProduct = productRepo.save(product);
		return savedProduct;
	}

	@Override
	public void deleteProduct(Long productId) throws ProductException {

		Product product = findProductById(productId);
		product.getSizes().clear();
		productRepo.delete(product);

	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		Product product = findProductById(productId);

		if (req.getQuantity() > 0) {
			product.setQuantity(req.getQuantity());
		}

		return product;
	}

	@Override
	public Product findProductById(Long productId) throws ProductException {
		Optional<Product> optional = productRepo.findById(productId);
		if (optional.isPresent()) {
			return optional.get();
		}

		throw new ProductException("Product Not Found With id " + productId);
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

		PageRequest pageble = PageRequest.of(pageNumber, pageSize);

		List<Product> product = productRepo.filterProduct(category, minPrice, maxPrice, minDiscount, sort);

		if (!colors.isEmpty()) {
			product = product.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());
		}

		if (stock != null) {
			if (stock.equals("in_stock")) {
				product = product.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
			} else if (stock.equals("out_of_stock")) {
				product = product.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
			}
		}	
		int startIndex = (int) pageble.getOffset();

		int endIndex = Math.min(startIndex + pageble.getPageSize(), product.size());
		List<Product> pageContent = product.subList(startIndex, endIndex);
		Page<Product> filterProduct = new PageImpl<>(pageContent, pageble, product.size());
		return filterProduct;
	}

	@Override
	public List<Product> findAllProduct() {
		List<Product> products = productRepo.findAll();
		return products;
	}

}
