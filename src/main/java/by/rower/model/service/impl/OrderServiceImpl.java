package by.rower.model.service.impl;


import by.rower.model.dao.OrderDao;
import by.rower.model.dto.OrderDto;
import by.rower.model.entity.Order;
import by.rower.model.service.OrderService;
import by.rower.model.util.LoggerUtil;
import by.rower.model.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
@Slf4j
public class OrderServiceImpl extends GenericServiceImpl<OrderDto,Long,Order> implements OrderService {

    private final OrderDao orderDao;

    @Override
    public List<OrderDto> findOrderByUsername(String username, int limit, int offset) {
        List<Order> orders = orderDao.findAllByUsername(username, limit, offset);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, orders, username);
        return mapper.mapToListDto(orders);
    }

    @Override
    public List<Long> getCountPages(String username) {
        Long countRow = orderDao.getCountRow(username);
        log.debug(LoggerUtil.COUNT_ROW_WAS_FOUND_IN_SERVICE, countRow);
        return ServiceUtil.collectPages(countRow);
    }
}
