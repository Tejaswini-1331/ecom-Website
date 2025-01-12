package example.ecommerce.website.service;

import example.ecommerce.website.model.Category;
import example.ecommerce.website.payload.CategoryDTO;
import example.ecommerce.website.payload.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long id);
   CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id);



}
