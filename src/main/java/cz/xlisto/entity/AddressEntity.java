package cz.xlisto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity(name = "addresses")
@Data
@NoArgsConstructor
public class AddressEntity {

    @Id
    @SequenceGenerator(name = "addresses_seq", sequenceName = "addresses_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addresses_seq")
    private Long id;

    @NonNull
    private String street;

    @NonNull
    @Column(length = 20)
    @Pattern(regexp = "[\\d]*")
    private String registry_number;

    @NonNull
    @Column(length = 20)
    @Pattern(regexp = "[\\d]+")
    private String house_number;

    @NonNull
    @Column(length = 50)
    private String city;

    @NonNull
    @Column(length = 10)
    @Pattern(regexp = "[\\d]{5}|[\\d]{3} [\\d]{2}")
    private String zip;

}
