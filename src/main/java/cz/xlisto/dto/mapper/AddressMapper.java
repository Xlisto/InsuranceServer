package cz.xlisto.dto.mapper;

import cz.xlisto.dto.AddressDTO;
import cz.xlisto.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class AddressMapper {

    public abstract AddressEntity toEntity(AddressDTO source);

    public abstract AddressDTO toDTO(AddressEntity source);
}
