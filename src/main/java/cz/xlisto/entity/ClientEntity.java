package cz.xlisto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity(name = "clients")
@Data
@NoArgsConstructor
public class ClientEntity {

    @Id
    @SequenceGenerator(name = "clients_seq", sequenceName = "clients_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_seq")
    private Long id;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL
            )
    @JoinColumn(name = "address_id")
    private AddressEntity address;


    /*@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL
    )
    @JoinColumn(name = "phone_id")
    private PhoneEntity phone;*/

}
