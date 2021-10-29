package cz.xlisto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity(name = "phone")
@Data
@NoArgsConstructor
public class PhoneEntity {

    @Id
    @SequenceGenerator(name = "phone_seq", sequenceName = "phone_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_seq")
    private Long id;

    @Column(length = 20, name="phone_number")
    @Pattern(regexp = "([+][\\d]{3})? ?[\\d]{3} ?[\\d]{3} ?[\\d]{3}")
    private String PhoneNumber;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClientEntity client;
}
