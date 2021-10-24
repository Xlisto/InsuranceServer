package cz.xlisto.dto.mapper;

import cz.xlisto.dto.InsuranceCarCategoriesDTO;
import cz.xlisto.entity.InsuranceCarCategoriesEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class InsuranceCarCategoriesMapper {

    public abstract InsuranceCarCategoriesEntity toEntity(InsuranceCarCategoriesDTO source);

    public abstract InsuranceCarCategoriesDTO toDTO(InsuranceCarCategoriesEntity source);
}
