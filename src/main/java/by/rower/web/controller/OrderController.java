package by.rower.web.controller;

import by.rower.model.dto.BicycleDto;
import by.rower.model.dto.OrderDto;
import by.rower.model.dto.user.AccountDto;
import by.rower.model.service.BicycleService;
import by.rower.model.service.OrderService;
import by.rower.web.util.LoggerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static by.rower.web.util.PageUtil.*;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final BicycleService bicycleService;

    @GetMapping("/user/showUserOrder")
    public String showUserOrder(@SessionAttribute(ACCOUNT_ATTRIBUTE) AccountDto account,
                                Model model,
                                @RequestParam(name = OFFSET_PARAMETER, defaultValue = VALUE_ZERO) String offset) {
        model.addAttribute(COUNT_PAGES_ATTRIBUTE, orderService.getCountPages(account.getUsername()));
        model.addAttribute(USER_ORDER_ATTRIBUTE, orderService.findOrderByUsername(account.getUsername(), LIMIT_TEN, Integer.parseInt(offset)));
        return USER_PREFIX + USER_ORDER_PAGE;
    }

    @PostMapping("/user/newOrder/{id}")
    public String userOrderById(@PathVariable(ID_PATH_VARIABLE) String id, @SessionAttribute(ACCOUNT_ATTRIBUTE) AccountDto account,
                                Model model,
                                @RequestParam(name = OFFSET_PARAMETER, defaultValue = VALUE_ZERO) String offset) {
        Optional<BicycleDto> bicycle = bicycleService.findById(Long.parseLong(id));
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_CONTROLLER_BY, bicycle, id);
        bicycle.ifPresent(stationDto -> model.addAttribute(BICYCLE_ATTRIBUTE, bicycle));
        return USER_PREFIX + ORDER_BLANC_PAGE;
    }

    @GetMapping("/user/newOrder/{id}")
    public String userOrder(@PathVariable(ID_PATH_VARIABLE) String id, Model model,
                            @SessionAttribute(ACCOUNT_ATTRIBUTE) AccountDto account) {
        Optional<BicycleDto> bicycle = bicycleService.findById(Long.parseLong(id));
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_CONTROLLER_BY, bicycle, id);
        model.addAttribute(USER_ORDER_ATTRIBUTE, OrderDto.builder().build());

        bicycle.ifPresent(OrderDto -> model.
                addAttribute(USER_ORDER_ATTRIBUTE, OrderDto.builder()
                        .build()));
        return USER_PREFIX + ORDER_BLANC_PAGE;
    }

    @PostMapping("/user/addOrder")
    public String addOrderById(
            @SessionAttribute(ACCOUNT_ATTRIBUTE) AccountDto user,
            @RequestParam(ID_PATH_VARIABLE) String id,
            @RequestParam(RENTAL_PERIOD_PARAMETER) String period,
            Model model) {
        if (Objects.nonNull(user)) {
            Optional<BicycleDto> bicycle = bicycleService.findById(Long.parseLong(id));
            OrderDto orderDto = OrderDto.builder()
                    .bicycle(bicycle.get())
                    .user(user)
                    .rentalTime(LocalDateTime.now())
                    .rentalPeriod(setRentalPeriod(period))
                    .orderPay(Double.parseDouble(period) * PAY_IN_DAY)
                    .build();
            if (Objects.nonNull(orderService.save(orderDto))) {
                log.debug(LoggerUtil.ENTITY_WAS_SAVED_IN_CONTROLLER, orderDto);
                model.addAttribute(ORDER_BLANC_ATTRIBUTE, orderDto);
                return USER_PREFIX + USER_PAGE_SUFFIX;
            }
            return REDIRECT + ERROR_PAGE;
        }
        return REDIRECT + ERROR_PAGE;
    }
}