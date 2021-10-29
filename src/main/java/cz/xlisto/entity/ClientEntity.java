package cz.xlisto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity(name = "clients")
@Table(indexes = @Index(name = "pinUniqIndex", columnList = "pin", unique = true))
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

    @NonNull
    @Column(name = "pin", length = 30)
    @Pattern(regexp = "[\\d]{6}/[\\d]{3,4}")
    private String pin;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "address_id")
    private AddressEntity address;


}
