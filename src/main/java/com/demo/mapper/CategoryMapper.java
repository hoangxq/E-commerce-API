package com.demo.mapper;

import org.mapstruct.*;

import com.demo.DTO.CategoryDTO;
import com.demo.models.Category;
import com.demo.service.CategoryService;

@Mapper(componentModel = "spring", uses = CategoryService.class)
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category>{

}
