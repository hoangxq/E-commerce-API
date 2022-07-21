package com.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.DTO.CategoryDTO;
import com.demo.models.Category;
import com.demo.payload.respone.MessageRespone;
import com.demo.repository.CategoryRepository;
import com.demo.service.CategoryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("")
	public ResponseEntity<?> getAllCategory(){
//		List<Category> listCategory = categoryRepository.findAll();
//		
//		return ResponseEntity.ok(listCategory.stream()
//				.map(e -> e.toDTO()));
		
		return ResponseEntity.ok(categoryService.findAll());
	}
	
	@GetMapping("/find")
	public ResponseEntity<?> getCategoryByName(@Valid @RequestBody CategoryDTO categoryDTO){
		
		if (categoryDTO.getName().trim().isBlank())
			return ResponseEntity.ok(new MessageRespone("Name of category is Empty"));
		
		Category  category = categoryRepository.findByName(categoryDTO.getName());
		
		if (category == null)
			return ResponseEntity.ok(new MessageRespone("This category is not exist"));
		
		return ResponseEntity.ok(category.toDTO());
	}
	
	@PostMapping("add-category")
	public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		
		if (categoryDTO.isBlank())
			return ResponseEntity.ok(new MessageRespone("Name or Description of category is Empty"));
		
		Category  category = categoryRepository.findByName(categoryDTO.getName());
		
		if (category != null)
			return ResponseEntity.ok(new MessageRespone("This category is existed"));
		
		category = new Category(categoryDTO.getName(), categoryDTO.getDescription());
		
		categoryRepository.save(category);
		return ResponseEntity.ok(category.toDTO());
		
	}
	
	@PutMapping("/change-category")
	public ResponseEntity<?> changeCategoryByName(@Valid @RequestBody CategoryDTO categoryDTO){
		
		if (categoryDTO.getName().trim().isBlank())
			return ResponseEntity.ok(new MessageRespone("Name of category is Empty"));
		
		Category  category = categoryRepository.findByName(categoryDTO.getName());
		
		if (category == null)
			return ResponseEntity.ok(new MessageRespone("This category is not exist"));
		
		if (categoryDTO.isBlank())
			return ResponseEntity.ok(new MessageRespone("Name or Description of category is Empty"));
		
		category.setName(categoryDTO.getName());
		category.setDescription(categoryDTO.getDescription());
		categoryRepository.save(category);
		
		return ResponseEntity.ok(new MessageRespone("Category is changed"));
	}
	
	@DeleteMapping("/delete-category")
	public ResponseEntity<?> deleteCategoryByName(@Valid @RequestBody CategoryDTO categoryDTO){
		
		if (categoryDTO.isBlank())
			return ResponseEntity.ok(new MessageRespone("Name of category is Empty"));
		
		Category  category = categoryRepository.findByName(categoryDTO.getName());
		
		if (category == null)
			return ResponseEntity.ok(new MessageRespone("This category is not exist"));
		
		categoryRepository.delete(category);
		return ResponseEntity.ok(new MessageRespone("Category is droped"));
		
	}
	
}
