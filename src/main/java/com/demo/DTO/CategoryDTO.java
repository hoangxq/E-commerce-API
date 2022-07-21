package com.demo.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
	
	private Long id;
	private String name;
	private String description;
	private List<String> list_product;
	
	public CategoryDTO(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	public boolean isBlank() {
		if (this.name.trim().isEmpty() || this.description.trim().isEmpty())
			return true;
		return false;
	}
		
}
