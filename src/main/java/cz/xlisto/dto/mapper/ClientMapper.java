package cz.xlisto.dto.mapper;

import cz.xlisto.dto.ClientDTO;
import cz.xlisto.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, PhoneMapper.class})
public abstract class ClientMapper {

    @Mappings({
            @Mapping(target = "address.street", source = "street"),
            @Mapping(target = "address.house_number", source = "houseNumber"),
            @Mapping(target = "address.registry_number", source = "registryNumber"),
            @Mapping(target = "address.zip", source = "zip"),
            @Mapping(target = "address.city", source = "city"),
            @Mapping(target = "id", source = "clientId"),

    })
    public abstract ClientEntity toEntity(ClientDTO source);


    @Mappings({
            @Mapping(target = "clientId", source = "id"),
            @Mapping(target = "street", source = "address.street"),
            @Mapping(target = "houseNumber", source = "address.house_number"),
            @Mapping(target = "registryNumber", source = "address.registry_number"),
            @Mapping(target = "zip", source = "address.zip"),
            @Mapping(target = "city", source = "address.city")
    })
    public abstract ClientDTO toDTO(ClientEntity source);
}
