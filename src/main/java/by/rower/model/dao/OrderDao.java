package by.rower.model.dao;

import by.rower.model.entity.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Long, Order>{

    List<Order> findAllByUsername(String username, int limit, int offset);

    Long getCountRow(String username);

}
