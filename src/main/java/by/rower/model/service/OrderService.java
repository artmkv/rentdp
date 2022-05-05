package by.rower.model.service;


import by.rower.model.dto.OrderDto;
import by.rower.model.entity.Order;

import java.util.List;

public interface OrderService extends GenericService<OrderDto,Long, Order> {

    List<OrderDto> findOrderByUsername(String username, int limit, int offset);

    List<Long> getCountPages(String username);
}
