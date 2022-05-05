package by.rower.model.mapper.impl;

import by.rower.model.dto.StationDto;
import by.rower.model.entity.Station;
import by.rower.model.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class StationMapper implements Mapper<Station, StationDto> {


    @Override
    public StationDto mapToDto(Station station) {
        if (Objects.nonNull(station)) {
            return StationDto.builder()
                    .id(station.getId())
                    .number(station.getNumber())
                    .capacity(station.getCapacity())
                    .build();
        }
        return null;
    }

    @Override
    public List<StationDto> mapToListDto(List<Station> stations) {
        if (Objects.nonNull(stations)) {
            List<StationDto> sectionDtoList = new ArrayList<>();
            for (Station section : stations) {
                sectionDtoList.add(mapToDto(section));
            }
            return sectionDtoList;
        }
        return Collections.emptyList();
    }

    @Override
    public Station mapToEntity(StationDto stationDto) {
        if (Objects.nonNull(stationDto)) {
            Station section = Station.builder()
                    .number(stationDto.getNumber())
                    .capacity(stationDto.getCapacity())
                    .build();
            section.setId(stationDto.getId());
            return section;
        }
        return null;
    }
}
