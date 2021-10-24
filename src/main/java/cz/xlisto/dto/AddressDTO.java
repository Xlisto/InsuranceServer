package cz.xlisto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Long address_id;

    private String street;

    @Column(name = "registry_number")
    private String registryNumber;

    @Column(name = "house_number")
    private String houseNumber;

    private String city;

    private Integer zip;
}
