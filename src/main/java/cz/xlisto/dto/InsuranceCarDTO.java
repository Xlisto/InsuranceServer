package cz.xlisto.dto;

import cz.xlisto.entity.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceCarDTO {

    private Long id;

    private Date start;

    private String registrationMark;

    private String brand;

    private Long enginePower;

    private Long clientId;
}
