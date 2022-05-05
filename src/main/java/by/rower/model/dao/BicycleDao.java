package by.rower.model.dao;

import by.rower.model.entity.Bicycle;
import by.rower.model.entity.user.BicycleStatus;

import java.util.List;
import java.util.Optional;

public interface BicycleDao extends GenericDao<Long, Bicycle>{

    Optional<Bicycle> findByNumber(long number);

    List<Bicycle> findAllByModel(String model, int limit, int offset);

    List<Bicycle> findAllByStatus(BicycleStatus status, int limit, int offset);

    List<Bicycle> findAllByStationId(Long stationId, int limit, int offset);

    List<Bicycle> findAllByParam(Long stationId, BicycleStatus status,  int limit, int offset);

}
