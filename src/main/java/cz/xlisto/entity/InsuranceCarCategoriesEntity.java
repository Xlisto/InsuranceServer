package cz.xlisto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(indexes = @Index(name = "enginePowerUniqIndex", columnList = "engine_power", unique = true))
@Entity (name = "insurance_car_categories")
public class InsuranceCarCategoriesEntity {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "insurance_car_categories_seq", sequenceName = "insurance_car_categories_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurance_car_categories_seq")
    private Long id;

    @NonNull
    @Column(name = "engine_power")
    private Long enginePower;

    @NonNull
    private Long cost;
}
