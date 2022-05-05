package by.rower.model.dto;
import by.rower.model.dto.user.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class OrderDto implements Serializable {

    private Long id;
    private BicycleDto bicycle;
    private AccountDto user;
    @DateTimeFormat()
    @PastOrPresent
    private LocalDateTime rentalTime;
    @DateTimeFormat()
    private LocalDateTime rentalPeriod;
    private double orderPay;
}

