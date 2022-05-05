package by.rower.model.dao;

import by.rower.model.entity.Station;

import java.util.Optional;

public interface StationDao extends GenericDao<Long, Station>{

    Optional<Station> findByNumber(long number);

}
