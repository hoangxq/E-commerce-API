package com.demo.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.demo.DTO.CategoryDTO;
import com.demo.mapper.CategoryMapper;
import com.demo.models.Category;

@Component
public class CategoryMapperImpl implements CategoryMapper{

	@Override
	public Category toEntity(CategoryDTO dto) {
		if ( dto == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( dto.getId() );
        category.setName( dto.getName() );
        category.setDescription( dto.getDescription() );

        return category;
	}

	@Override
	public CategoryDTO toDto(Category entity) {
		if ( entity == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( entity.getId() );
        categoryDTO.setName( entity.getName() );
        categoryDTO.setDescription( entity.getDescription() );

        return categoryDTO;
	}

	@Override
	public List<Category> toEntity(List<CategoryDTO> dtoList) {
		if ( dtoList == null ) {
            return null;
        }

        return dtoList.stream().map(e -> toEntity(e)).collect(Collectors.toList());
	}

	@Override
	public List<CategoryDTO> toDto(List<Category> entityList) {
		if ( entityList == null ) {
            return null;
        }

        return entityList.stream().map(e -> toDto(e)).collect(Collectors.toList());
	}

	@Override
	public void partialUpdate(Category entity, CategoryDTO dto) {
		// TODO Auto-generated method stub
		
	}

}
