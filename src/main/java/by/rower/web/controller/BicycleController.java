package by.rower.web.controller;


import by.rower.model.dto.BicycleDto;
import by.rower.model.dto.StationDto;
import by.rower.model.entity.user.BicycleStatus;
import by.rower.model.service.AccountService;
import by.rower.model.service.BicycleService;
import by.rower.model.service.StationService;
import by.rower.web.util.LoggerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static by.rower.web.util.PageUtil.*;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BicycleController {

    private final BicycleService bicycleService;
    private final StationService stationService;

    @ModelAttribute(ALL_STATIONS_ATTRIBUTE)
    public List<StationDto> allGenres() {
        return stationService.findAll(Integer.MAX_VALUE, OFFSET_ZERO);
    }

    @ModelAttribute(ALL_STATUS_ATTRIBUTE)
    public List<BicycleStatus> allStatus() {
        return Arrays.asList(BicycleStatus.values());
    }

    @GetMapping("/admin/editBicycle")
    public String editBicyclePage(Model model){
        model.addAttribute(BICYCLE_ATTRIBUTE, new BicycleDto());
        return ADMIN_PREFIX + EDIT_BICYCLE_SUFFIX;
    }

    @PostMapping("/editBicycle")
    public String editBicycle(@ModelAttribute(BICYCLE_ATTRIBUTE) @Valid BicycleDto bicycle, Errors errors) {
        if (errors.hasErrors()) {
            return ADMIN_PREFIX + EDIT_BICYCLE_SUFFIX;
        }
        if (Objects.nonNull(bicycle)) {
            bicycleService.update(bicycle);
            log.debug(LoggerUtil.ENTITY_WAS_UPDATED_IN_CONTROLLER, bicycle);
            return REDIRECT + ALL_BICYCLES_ADMIN_PAGE;
        }
        return REDIRECT + ERROR_PAGE;
    }

    @GetMapping("/admin/editBicycle/{id}")
    public String editBicycleIdPage(@PathVariable(ID_PATH_VARIABLE) String id, Model model) {
        Optional<BicycleDto> bicycleById = bicycleService.findById(Long.parseLong(id));
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_CONTROLLER_BY, bicycleById, id);
        bicycleById.ifPresent(bicycleDto -> model.addAttribute(BICYCLE_ATTRIBUTE, bicycleDto));
        return ADMIN_PREFIX + EDIT_BICYCLE_SUFFIX;
    }

    @PostMapping("/admin/editBicycle/{id}")
    public String editStationId(Model model, @ModelAttribute(STATION_ATTRIBUTE) @Valid BicycleDto bicycle, Errors errors) {
        if (errors.hasErrors()) {
            return ADMIN_PREFIX + EDIT_BICYCLE_SUFFIX;
        }
        if (Objects.nonNull(bicycle.getNumber()) && Objects.nonNull(bicycle.getVin())) {
            bicycleService.update(bicycle);
            log.debug(LoggerUtil.ENTITY_WAS_UPDATED_IN_CONTROLLER, bicycle);
            model.addAttribute(BICYCLE_ATTRIBUTE, bicycle);
            return REDIRECT + ADMIN_PAGE;
        } else {
            return REDIRECT + ERROR_PAGE;
        }
    }

    @GetMapping("/addBicycle")
    public String addBicyclePage(Model model) {
        model.addAttribute(BICYCLE_ATTRIBUTE, BicycleDto.builder()
                        .station(StationDto.builder().build())
                .build());
        return ADMIN_PREFIX + ADD_BICYCLE_SUFFIX;
    }

    @PostMapping("/addBicycle")
    public String addBicycle(@ModelAttribute(BICYCLE_ATTRIBUTE) @Valid BicycleDto bicycle, Errors errors) {
        if (errors.hasErrors()) {
            return ADMIN_PREFIX + ADD_BICYCLE_SUFFIX;
        }
        if (Objects.nonNull(bicycle)) {
            bicycleService.save(bicycle);
            log.debug(LoggerUtil.ENTITY_WAS_SAVED_IN_CONTROLLER, bicycle);
            return REDIRECT + ADMIN_PAGE;
        }
        return REDIRECT + ERROR_PAGE;
    }

    @GetMapping({"/admin/allBicycles"})
    public String showAdminAllBicycles(Model model,
                                  @RequestParam(name = OFFSET_PARAMETER, defaultValue = VALUE_ZERO) String offset) {
        model.addAttribute(COUNT_PAGES_ATTRIBUTE, bicycleService.getCountPages());
        model.addAttribute(ALL_BICYCLES_ATTRIBUTE, bicycleService.findAll(LIMIT_TEN, Integer.parseInt(offset)));
        return ADMIN_PREFIX + ALL_BICYCLES_ATTRIBUTE;
    }

    @GetMapping({ "/user/allBicycles"})
    public String showUserAllBicycles(Model model,
                                  @RequestParam(name = OFFSET_PARAMETER, defaultValue = VALUE_ZERO) String offset) {
        model.addAttribute(COUNT_PAGES_ATTRIBUTE, bicycleService.getCountPages());
        model.addAttribute(ALL_BICYCLES_ATTRIBUTE, bicycleService.findAllByStatus(BicycleStatus.FREE, LIMIT_TEN, Integer.parseInt(offset)));
        return USER_PREFIX + ALL_BICYCLES_USER_ATTRIBUTE;
    }

    @GetMapping({ "/user/allBicyclesInStation/{id}"})
    public String showAllBicyclesByStationId(@PathVariable(ID_PATH_VARIABLE) String id, Model model,
                                      @RequestParam(name = OFFSET_PARAMETER, defaultValue = VALUE_ZERO) String offset) {
        model.addAttribute(COUNT_PAGES_ATTRIBUTE, bicycleService.getCountPages());
        model.addAttribute(ALL_BICYCLES_ATTRIBUTE, bicycleService.findAllBicyclesByParameters(Long.parseLong(id), BicycleStatus.FREE, LIMIT_TEN, Integer.parseInt(offset)));
        return USER_PREFIX + ALL_BICYCLES_USER_ATTRIBUTE;
    }

    @GetMapping({ "/user/orderBicycle/{id}"})
    public String orderBicycleById(@PathVariable(ID_PATH_VARIABLE) String id,
                                   Model model ){
        Optional<BicycleDto> bicycleById = bicycleService.findById(Long.parseLong(id));
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_CONTROLLER_BY, bicycleById, id);
        bicycleById.ifPresent(bicycleDto -> model.addAttribute(BICYCLE_ATTRIBUTE, bicycleDto));
        return USER_PREFIX + ORDER_BLANC_PAGE;
    }

    @GetMapping("/admin/deleteBicycle/{id}")
    public String deleteStation(@PathVariable(ID_PATH_VARIABLE) String id, Model model){
        Optional<BicycleDto> bicycleById = bicycleService.findById(Long.parseLong(id));
        bicycleById.ifPresent(bicycle -> {
            if ( Objects.isNull(bicycle.getId()) || (bicycle.getNumber() != 0) ) {
                bicycleService.delete(bicycle);
                log.debug(LoggerUtil.ENTITY_WAS_DELETED_IN_CONTROLLER, bicycle);
            } else {
                model.addAttribute(CANNOT_DELETE_ATTRIBUTE, true);
            }
        });
        return ADMIN_PREFIX + ALL_BICYCLES_PAGE;
    }
}
