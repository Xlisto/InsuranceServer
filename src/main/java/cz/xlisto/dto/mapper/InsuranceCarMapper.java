package cz.xlisto.dto.mapper;

import cz.xlisto.dto.InsuranceCarDTO;
import cz.xlisto.entity.InsuranceCarEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class InsuranceCarMapper {

    @Mappings({
            @Mapping(target = "client.id", source = "clientId"),
            //@Mapping(target = "id", source = "phoneId"),
            //@Mapping(target = "phone", expression = "java(getPhone(source))")
    })
    public abstract InsuranceCarEntity toEntity(InsuranceCarDTO source);

    //@Mapping(target = "clientId", source = "client.id")
    public abstract InsuranceCarDTO toDTO(InsuranceCarEntity source);
}
