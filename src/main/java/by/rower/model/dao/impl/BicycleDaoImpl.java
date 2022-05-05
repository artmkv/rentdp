package by.rower.model.dao.impl;

import by.rower.model.dao.BicycleDao;
import by.rower.model.entity.Bicycle;
import by.rower.model.entity.Bicycle_;
import by.rower.model.entity.Station;
import by.rower.model.entity.Station_;
import by.rower.model.entity.user.BicycleStatus;
import by.rower.model.util.LoggerUtil;
import org.hibernate.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class BicycleDaoImpl extends GenericDaoImpl<Long, Bicycle> implements BicycleDao {

    @Override
    public Optional<Bicycle> findByNumber(long number) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Bicycle> criteria = cb.createQuery(Bicycle.class);
        Root<Bicycle> root = criteria.from(Bicycle.class);
        criteria.select(root).where(cb.equal(root.get(Bicycle_.number), number));
        Bicycle bicycle = session.createQuery(criteria).uniqueResult();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, bicycle, number);
        return Optional.ofNullable(bicycle);
    }

    @Override
    public List<Bicycle> findAllByModel(String model, int limit, int offset) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Bicycle> criteria = cb.createQuery(Bicycle.class);
        Root<Bicycle> from = criteria.from(Bicycle.class);
        criteria.select(from).where(cb.equal(from.get(Bicycle_.model), model));
        List<Bicycle> resultList = session
                .createQuery(criteria.select(from))
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, model);
        return resultList;
    }

    @Override
    public List<Bicycle> findAllByStatus(BicycleStatus status, int limit, int offset) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Bicycle> criteria = cb.createQuery(Bicycle.class);
        Root<Bicycle> from = criteria.from(Bicycle.class);
        criteria.select(from).where(cb.equal(from.get(Bicycle_.status), status));
        List<Bicycle> resultList = session
                .createQuery(criteria.select(from))
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, status);
        return resultList;
    }

    public List<Bicycle> findAllByStationId(Long stationId, int limit, int offset) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Bicycle> criteria = cb.createQuery(Bicycle.class);
        Root<Bicycle> from = criteria.from(Bicycle.class);
        Join<Bicycle, Station> stationJoin = from.join(Bicycle_.station);
        criteria.select(from).where(cb.equal(stationJoin.get(Station_.ID), stationId));
        List<Bicycle> resultList = session
                .createQuery(criteria.select(from))
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, stationId);
        return resultList;
    }
    public List<Bicycle> findAllByParam(Long stationId, BicycleStatus status,  int limit, int offset) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Bicycle> criteria = cb.createQuery(Bicycle.class);
        Root<Bicycle> from = criteria.from(Bicycle.class);
        criteria.select(from)
                .where(cb.and(
                        cb.equal(from.get(Bicycle_.station), stationId),
                        cb.equal(from.get(Bicycle_.status), status)
                ));
        List<Bicycle> resultList = session
                .createQuery(criteria.select(from))
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_DAO_BY, resultList, stationId);
        return resultList;
    }

}
