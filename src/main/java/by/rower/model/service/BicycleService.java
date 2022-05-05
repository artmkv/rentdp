package by.rower.model.service;

import by.rower.model.dto.BicycleDto;
import by.rower.model.entity.Bicycle;
import by.rower.model.entity.user.BicycleStatus;

import java.util.List;
import java.util.Optional;

public interface BicycleService extends GenericService<BicycleDto, Long, Bicycle> {

    Optional<BicycleDto> findByNumber(long number);

    List<BicycleDto> findAllByModel(String model, int limit, int offset);

    List<BicycleDto> findAllByStatus(BicycleStatus status, int limit, int offset);

    List<BicycleDto> findAllBicyclesByStationId(Long stationId, int limit, int offset);

    List<BicycleDto> findAllBicyclesByParameters(Long stationId, BicycleStatus status, int limit, int offset);

}
