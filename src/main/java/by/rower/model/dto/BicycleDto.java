package by.rower.model.dto;


import by.rower.model.entity.user.BicycleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class BicycleDto implements Serializable {

    private Long id;
    @NotNull
    private long number;
    @NotNull
    private String model;
    @Min(4)
    private long vin;
    private long stationId;
    @NotNull
    private StationDto station;
    private BicycleStatus status;
}
