package by.rower.model.service;

import by.rower.model.dto.StationDto;
import by.rower.model.entity.Station;

import java.util.Optional;

public interface StationService extends GenericService<StationDto, Long, Station>{

    Optional<StationDto> findByNumber(long number);


}
