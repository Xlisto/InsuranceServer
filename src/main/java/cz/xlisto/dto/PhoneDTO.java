package cz.xlisto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {

    private Long phoneId;

    private String phoneNumber;

    private Long clientId;
}
