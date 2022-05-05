package by.rower.model.service.impl;

import by.rower.model.dao.StationDao;
import by.rower.model.dto.StationDto;
import by.rower.model.entity.Station;
import by.rower.model.service.StationService;
import by.rower.model.util.LoggerUtil;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StationServiceImpl extends GenericServiceImpl<StationDto, Long, Station> implements StationService {

    private final StationDao stationDao;

    @Override
    public Optional<StationDto> findByNumber(long number) {
        Optional<Station> optionalSection = stationDao.findByNumber(number);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, optionalSection, number);
        return Optional.ofNullable(mapper.mapToDto(optionalSection.orElse(null)));
    }
}
