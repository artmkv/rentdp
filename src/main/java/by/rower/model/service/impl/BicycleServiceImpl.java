package by.rower.model.service.impl;

import by.rower.model.dao.BicycleDao;
import by.rower.model.dto.BicycleDto;
import by.rower.model.entity.Bicycle;
import by.rower.model.entity.user.BicycleStatus;
import by.rower.model.service.BicycleService;
import by.rower.model.util.LoggerUtil;

import java.util.List;
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
public class BicycleServiceImpl extends GenericServiceImpl<BicycleDto, Long, Bicycle> implements BicycleService {

    private final BicycleDao bicycleDao;

    @Override
    public Optional<BicycleDto> findByNumber(long number) {
        Optional<Bicycle> optionalSection = bicycleDao.findByNumber(number);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, optionalSection, number);
        return Optional.ofNullable(mapper.mapToDto(optionalSection.orElse(null)));
    }

    @Override
    public List<BicycleDto> findAllByModel(String model, int limit, int offset) {
        List<Bicycle> bicycles = bicycleDao.findAllByModel(model, limit, offset);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, bicycles, model);
        return mapper.mapToListDto(bicycles);
    }

    @Override
    public List<BicycleDto> findAllByStatus(BicycleStatus status, int limit, int offset) {
        List<Bicycle> bicycles = bicycleDao.findAllByStatus(status, limit, offset);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, bicycles, status);
        return mapper.mapToListDto(bicycles);
    }

    @Override
    public List<BicycleDto> findAllBicyclesByStationId(Long stationId, int limit, int offset) {
        List<Bicycle> bicycles = bicycleDao.findAllByStationId(stationId, limit, offset);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE, bicycles);
        return mapper.mapToListDto(bicycles);
    }

    @Override
    public List<BicycleDto> findAllBicyclesByParameters(Long stationId, BicycleStatus status, int limit, int offset) {
        List<Bicycle> bicycles = bicycleDao.findAllByParam(stationId, status,  limit, offset);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE, bicycles);
        return mapper.mapToListDto(bicycles);
    }
}
