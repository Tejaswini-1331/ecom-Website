package example.ecommerce.website.controller;

import example.ecommerce.website.configuration.AppConstants;
import example.ecommerce.website.model.Category;
import example.ecommerce.website.payload.CategoryDTO;
import example.ecommerce.website.payload.CategoryResponse;
import example.ecommerce.website.repositories.CategoryRepository;
import example.ecommerce.website.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    //object of categoryService is autowired
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategory(
            @RequestParam(name="pageNumber" , defaultValue = AppConstants.page_number, required=false) Integer pageNumber,
            @RequestParam(name="pageSize",defaultValue = AppConstants.page_size,required=false) Integer pageSize,
            @RequestParam(name="sortBy", defaultValue = AppConstants.sort_categories_by, required=false) String sortBy,
            @RequestParam(name="sortOrder", defaultValue = AppConstants.sort_order_by,required=false) String sortOrder
    )
    {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }

/*
    @GetMapping("/api/public/categories/{id")
    public ResponseEntity<CategoryResponse> getAllCategoryById(@PathVariable Long id)
    {

        return new ResponseEntity<>("categoryResponse not found",HttpStatus.OK);
    }*/




    @PostMapping("/api/public/categories")
    public ResponseEntity<CategoryDTO> postCat(@Valid @RequestBody CategoryDTO categoryDTO){

        CategoryDTO savedcategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedcategoryDTO,HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> deleteCat(@PathVariable Long id){

            CategoryDTO deletedCategory = categoryService.deleteCategory(id);
            //return new ResponseEntity<>(status, HttpStatus.OK);
            //if category found we will get status has category found and httpstatus is ok.
            return new ResponseEntity<>(deletedCategory,HttpStatus.OK);

    }

    //updating
    @PutMapping("/api/public/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
                                                 @RequestBody CategoryDTO categoryDTO){
            CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO,id);
            return new ResponseEntity<>(savedCategoryDTO,HttpStatus.OK);


    }


}
