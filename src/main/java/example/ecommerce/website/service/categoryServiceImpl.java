package example.ecommerce.website.service;

import example.ecommerce.website.exceptions.APIException;
import example.ecommerce.website.exceptions.ResourceNotFoundException;
import example.ecommerce.website.model.Category;
import example.ecommerce.website.payload.CategoryDTO;
import example.ecommerce.website.payload.CategoryResponse;
import example.ecommerce.website.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.util.List;


@Service
public class categoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize, String sortBy,String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        //getting pagedetails
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByAndOrder);
        Page<Category> categorypage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categorypage.getContent();

        if(categories.isEmpty()) throw new APIException("no categories to show");

        List<CategoryDTO> categoryDTOS = categories.stream()
                //each category is mapped to dto class
                .map(category -> modelMapper.map(category,CategoryDTO.class)).toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categorypage.getNumber());
        categoryResponse.setPageSize(categorypage.getSize());
        categoryResponse.setTotalPages(categorypage.getTotalPages());
        categoryResponse.setTotalElements(categorypage.getTotalElements());
        categoryResponse.setLastPage(categorypage.isLast());
        return categoryResponse;
    }




    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category =modelMapper.map(categoryDTO,Category.class);
        Category savedCategoryFromDb = categoryRepository.findByName(category.getName());
        if(savedCategoryFromDb != null) throw new APIException("Category already exists WITH THE NAME  " + savedCategoryFromDb.getName());
        categoryRepository.save(category);
        Category savedCategory = categoryRepository.save(category);
       return modelMapper.map(savedCategory,CategoryDTO.class);
    }




    @Override
    public CategoryDTO deleteCategory(Long id) {
        //searching for category on basis of ID to delete
        Category category = categoryRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Category","id",id));
        categoryRepository.delete(category);
        return modelMapper.map(category,CategoryDTO.class);
    }


    //logic for updating api
    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id) {
        Category savedCategory = categoryRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Category","id",id));

        Category category =modelMapper.map(categoryDTO,Category.class);
        category.setId(id);
        savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory,CategoryDTO.class);
    }




}
