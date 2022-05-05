package by.rower.web.controller;

import by.rower.model.dto.StationDto;
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
import java.util.Objects;
import java.util.Optional;

import static by.rower.web.util.PageUtil.*;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class StationController {

    private final StationService stationService;

    @GetMapping("/admin/editStation")
    public String editStationPage(Model model) {
        model.addAttribute(STATION_ATTRIBUTE, new StationDto());
        return ADMIN_PREFIX + EDIT_STATION_SUFFIX;
    }

    @PostMapping("/editStation")
    public String editStation(@ModelAttribute(STATION_ATTRIBUTE) @Valid StationDto station, Errors errors) {
            if (errors.hasErrors()) {
                return ADMIN_PREFIX + EDIT_STATION_SUFFIX;
            }
            if (Objects.nonNull(station.getNumber()) && Objects.nonNull(station.getCapacity())) {
                stationService.update(station);
                log.debug(LoggerUtil.ENTITY_WAS_UPDATED_IN_CONTROLLER, station);
                return REDIRECT + ADMIN_PAGE;
            }
            return REDIRECT + ERROR_PAGE;
        }

    @GetMapping("/admin/editStation/{id}")
    public String editStationIdPage(@PathVariable(ID_PATH_VARIABLE) String id, Model model) {
        Optional<StationDto> stationById = stationService.findById(Long.parseLong(id));
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_CONTROLLER_BY, stationById, id);
        stationById.ifPresent(stationDto -> model.addAttribute(STATION_ATTRIBUTE, stationDto));
        return ADMIN_PREFIX + EDIT_STATION_SUFFIX;
    }

    @PostMapping("/admin/editStation/{id}")
    public String editStationId(Model model, @ModelAttribute(STATION_ATTRIBUTE) @Valid StationDto station, Errors errors) {
        if (errors.hasErrors()) {
            return ADMIN_PREFIX + EDIT_STATION_SUFFIX;
        }
        if (Objects.nonNull(station.getNumber()) && Objects.nonNull(station.getCapacity())) {
            stationService.update(station);
            log.debug(LoggerUtil.ENTITY_WAS_UPDATED_IN_CONTROLLER, station);
            model.addAttribute(STATION_ATTRIBUTE, station);
            return REDIRECT + ADMIN_PAGE;
        } else {
            return REDIRECT + ERROR_PAGE;
        }
    }

    @GetMapping("/addStation")
    public String addStationPage(Model model) {
        model.addAttribute(STATION_ATTRIBUTE, new StationDto());
        return ADMIN_PREFIX + ADD_STATION_SUFFIX;
    }

    @PostMapping("/addStation")
    public String addStation(@ModelAttribute(STATION_ATTRIBUTE) @Valid StationDto station, Errors errors) {
        if (errors.hasErrors()) {
            return ADMIN_PREFIX + ADD_STATION_SUFFIX;
        }
        if (Objects.nonNull(station.getNumber()) && Objects.nonNull(station.getCapacity())) {
            stationService.save(station);
            log.debug(LoggerUtil.ENTITY_WAS_SAVED_IN_CONTROLLER, station);
            return REDIRECT + ADMIN_PAGE;
        }
        return REDIRECT + ERROR_PAGE;
    }

    @GetMapping({"/admin/allStations"})
    public String showAdminAllStations(Model model,
                               @RequestParam(name = OFFSET_PARAMETER, defaultValue = VALUE_ZERO) String offset) {
        model.addAttribute(COUNT_PAGES_ATTRIBUTE, stationService.getCountPages());
        model.addAttribute(ALL_STATIONS_ATTRIBUTE, stationService.findAll(LIMIT_TEN, Integer.parseInt(offset)));
        return ADMIN_PREFIX + ALL_STATIONS_PAGE;
    }

    @GetMapping({ "/user/allStations"})
    public String showUserAllStations(Model model,
                                  @RequestParam(name = OFFSET_PARAMETER, defaultValue = VALUE_ZERO) String offset) {
        model.addAttribute(COUNT_PAGES_ATTRIBUTE, stationService.getCountPages());
        model.addAttribute(ALL_STATIONS_ATTRIBUTE, stationService.findAll(LIMIT_TEN, Integer.parseInt(offset)));
        return USER_PREFIX + ALL_STATIONS_USER_PAGE;
    }

        @GetMapping("/admin/deleteStation/{id}")
    public String deleteStation(@PathVariable(ID_PATH_VARIABLE) String id, Model model){
        Optional<StationDto> stationById = stationService.findById(Long.parseLong(id));
        stationById.ifPresent(station -> {
            if ( Objects.isNull(station.getId()) || (station.getNumber() != 0) ) {
                stationService.delete(station);
                log.debug(LoggerUtil.ENTITY_WAS_DELETED_IN_CONTROLLER, station);
            } else {
                model.addAttribute(CANNOT_DELETE_ATTRIBUTE, true);
            }
        });
        return ADMIN_PREFIX + ALL_STATIONS_PAGE;
    }



}
