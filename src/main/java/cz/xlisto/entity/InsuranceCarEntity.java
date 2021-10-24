package cz.xlisto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "insurance_car")
@Data
@NoArgsConstructor
public class InsuranceCarEntity {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "insurance_car_seq", sequenceName = "insurance_car_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurance_car_seq")
    private Long id;

    @NonNull
    private Date start;

    @NonNull
    @Column(name = "registration_mark", length = 20)
    private String registrationMark;

    @NonNull
    @Column(length = 50)
    private String brand;

    @NonNull
    @Column(name = "engine_power")
    private Long enginePower;

    /*@NonNull
    private Long cost;*/

    @ManyToOne()
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClientEntity client;
}
