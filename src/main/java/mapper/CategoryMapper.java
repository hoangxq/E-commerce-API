package mapper;

import org.mapstruct.*;

import com.demo.DTO.CategoryDTO;
import com.demo.models.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category>{

}
