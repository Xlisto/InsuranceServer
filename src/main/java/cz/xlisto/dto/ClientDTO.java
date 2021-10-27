package cz.xlisto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long clientId;

    private String firstName;

    private String lastName;

    private String pin;

    private String street;

    private String houseNumber;

    private String registryNumber;

    private String zip;

    private String city;

    private List<PhoneDTO> phones;


}
