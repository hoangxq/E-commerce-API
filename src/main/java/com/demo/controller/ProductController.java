package com.demo.controller;

import java.util.List;
import java.util.Set;
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

import com.demo.DTO.ProductDTO;
import com.demo.models.Category;
import com.demo.models.Product;
import com.demo.payload.respone.MessageRespone;
import com.demo.repository.CategoryRepository;
import com.demo.repository.ProductRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("")
	public ResponseEntity<?> getAllProduct(){
		List<Product> listProduct = productRepository.findAll();
		return ResponseEntity.ok(listProduct.stream()
				.map(e -> e.toDTO())); 
	}
	
	@GetMapping("/find")
	public ResponseEntity<?> getProductByName(@Valid @RequestBody ProductDTO productDTO){
		
		if (productDTO.getName().trim().isEmpty())
			return ResponseEntity.ok(new MessageRespone("Name of product is Empty"));
		
		Product product = productRepository.findByName(productDTO.getName());
		
		if (product == null)
			return ResponseEntity.ok(new MessageRespone("This product is not exist"));
		
		return ResponseEntity.ok(product.toDTO());
		
	}
	
	@PostMapping("/add-product")
	public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO productDTO){
		
		Category category = categoryRepository.findByName(productDTO.getCategory());
	
		
		Product product = new Product(productDTO.getName(), productDTO.getDescription(), 
				productDTO.getQuantity(), productDTO.getPrice(), 
				productDTO.getPicture(), category);
		
		productRepository.save(product);
		
		Set<Product> list_product = category.getList_product();
		list_product.add(product);
		category.setList_product(list_product);
		
		categoryRepository.save(category);
		
		return ResponseEntity.ok(product.toDTO());
	}
	
	@PutMapping("/edit-product")
	public ResponseEntity<?> editProduct (@Valid @RequestBody ProductDTO productDTO){
		
		Product product = productRepository.findByName(productDTO.getName());
		
		Category categoryNew = categoryRepository.findByName(productDTO.getCategory());
		Category categoryOld = categoryRepository.findByName(product.getCategory().getName());
		
		product.setCategory(categoryNew);
		productRepository.save(product);
		
		Set<Product> listProduct = categoryOld.getList_product();
		listProduct.remove(product);
		
		categoryOld.setList_product(listProduct);
		categoryRepository.save(categoryOld);
		
		
		listProduct = categoryNew.getList_product();
		listProduct.add(product);
		
		categoryNew.setList_product(listProduct);
		categoryRepository.save(categoryNew);
		

		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setQuantity(productDTO.getQuantity());
		product.setPrice(productDTO.getPrice());
		product.setPicture(productDTO.getPicture());
		
		return ResponseEntity.ok(product.toDTO());
	}
	
	@DeleteMapping("/delete-product")
	public ResponseEntity<?> deleteProduct(@Valid @RequestBody ProductDTO productDTO){
		
		Product product = productRepository.findByName(productDTO.getName());
		
		Category category = categoryRepository.findByName(product.getCategory().getName());
		Set<Product> listProduct = category.getList_product();
		listProduct.remove(product);
		categoryRepository.save(category);
		
		productRepository.delete(product);
		
		return ResponseEntity.ok(new MessageRespone("Product is droped"));
		
	}
}
