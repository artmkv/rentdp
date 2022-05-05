package by.rower.model.mapper.impl;


import by.rower.model.dto.OrderDto;
import by.rower.model.entity.Order;
import by.rower.model.entity.user.User;
import by.rower.model.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderMapper implements Mapper<Order, OrderDto> {

    private final BicycleMapper bicycleMapper;
    private final AccountMapper accountMapper;

    @Override
    public OrderDto mapToDto(Order order) {
        if (Objects.nonNull(order)) {
            return OrderDto.builder()
                    .id(order.getId())
                    .bicycle(bicycleMapper.mapToDto(order.getBicycle()))
                    .user(accountMapper.mapToDto(order.getAccount()))
                    .orderPay(order.getOrderPay())
                    .rentalTime(order.getRentalTime())
                    .rentalPeriod(order.getRentalPeriod())
                    .build();
        }
        return null;
    }

    @Override
    public List<OrderDto> mapToListDto(List<Order> orders) {
        if (Objects.nonNull(orders)) {
            List<OrderDto> orderDtoList = new ArrayList<>();
            for (Order order : orders) {
                orderDtoList.add(mapToDto(order));
            }
            return orderDtoList;
        }
        return Collections.emptyList();
    }

    @Override
    public Order mapToEntity(OrderDto orderDto) {
        if (Objects.nonNull(orderDto)) {
            Order order = Order.builder()
                    .bicycle(bicycleMapper.mapToEntity(orderDto.getBicycle()))
                    .account((User) accountMapper.mapToEntity(orderDto.getUser()))
                    .orderPay(orderDto.getOrderPay())
                    .rentalTime(orderDto.getRentalTime())
                    .rentalPeriod(orderDto.getRentalPeriod())
                    .build();
            order.setId(orderDto.getId());
            return order;
        }
        return null;
    }
}
