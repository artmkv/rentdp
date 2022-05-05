package by.rower.model.entity;

import by.rower.model.entity.user.BicycleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(schema = "rowerby_hibernate")
public class Bicycle extends BaseEntity<Long>{

    @Column(name = "number", unique = true,nullable = false)
    private long number;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "vin", unique = true)
    private long vin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    @ToString.Exclude
    private Station station;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BicycleStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bicycle bicycle = (Bicycle) o;
        return id != null && Objects.equals(id, bicycle.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
