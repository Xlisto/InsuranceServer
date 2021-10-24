package cz.xlisto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceCarCategoriesDTO {

    private Long id;

    private Long enginePower;

    private Long cost;
}
