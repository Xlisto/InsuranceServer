package cz.xlisto.dto.mapper;

import cz.xlisto.dto.ClientDTO;
import cz.xlisto.dto.PhoneDTO;
import cz.xlisto.entity.PhoneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PhoneMapper {
    @Mappings({
            @Mapping(target = "client.id", source = "clientId"),
            @Mapping(target = "id", source = "phoneId"),
            //@Mapping(target = "phone", expression = "java(getPhone(source))")
    })
    public abstract PhoneEntity toEntity(PhoneDTO source);


    @Mappings({
            @Mapping(target = "clientId", source = "client.id"),
            @Mapping(target = "phoneId", source = "id"),
    })
    public abstract PhoneDTO toDTO(PhoneEntity source);

    /*protected List<String> getPhones(PhoneDTO source) {
        List<String> result = new ArrayList<>();
        for(PhoneDTO phoneDTO: source.);
    }*/
    /*protected String getPhone(PhoneDTO source) {
        System.out.println("Phone number "+source.toString());
        return "xxxx";
    }*/
}
