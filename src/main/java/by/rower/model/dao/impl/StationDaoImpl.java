package by.rower.model.dao.impl;

import by.rower.model.dao.StationDao;
import by.rower.model.entity.Station;
import by.rower.model.entity.Station_;
import by.rower.model.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class StationDaoImpl extends GenericDaoImpl<Long, Station> implements StationDao {

    @Override
    public Optional<Station> findByNumber(long number) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Station> criteria = cb.createQuery(Station.class);
        Root<Station> root = criteria.from(Station.class);
        criteria.select(root).where(cb.equal(root.get(Station_.number), number));
        Station station = session.createQuery(criteria).uniqueResult();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, station, number);
        return Optional.ofNullable(station);
    }
}
