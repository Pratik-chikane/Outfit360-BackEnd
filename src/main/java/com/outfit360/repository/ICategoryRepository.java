package com.outfit360.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.outfit360.model.Category;

public interface ICategoryRepository extends JpaRepository<Category, Long> {

	public Category findByName(String name);

	@Query("select c from Category c where c.name=:name and c.parentCategory.name=:parentCategoryName")
	public Category findByNameAndParent(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName );
}
