package com.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.DTO.CategoryDTO;
import com.demo.mapper.CategoryMapper;
import com.demo.repository.CategoryRepository;
import com.demo.service.impl.CategoryServiceImpl;

@Service
@Transactional
public class CategoryService implements CategoryServiceImpl{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Override
	public List<CategoryDTO> findAll() {
		return categoryRepository.findAll().stream()
				.map(categoryMapper::toDto).collect(Collectors.toList());
	}

}
