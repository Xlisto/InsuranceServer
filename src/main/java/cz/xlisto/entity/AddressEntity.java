package cz.xlisto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

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
    private String registry_number;

    @NonNull
    @Column(length = 20)
    private String house_number;

    @NonNull
    @Column(length = 50)
    private String city;

    @NonNull
    @Column(length = 10)
    private String zip;

    //Zatím nevím, bez toho funguje
    /*@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "address")
    private ClientEntity client;*/

}
