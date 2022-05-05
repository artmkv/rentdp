package by.rower.model.mapper.impl;

import by.rower.model.dto.BicycleDto;
import by.rower.model.entity.Bicycle;
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
public class BicycleMapper implements Mapper<Bicycle, BicycleDto> {

    private final StationMapper stationMapper;

    @Override
    public BicycleDto mapToDto(Bicycle bicycle) {
        if (Objects.nonNull(bicycle)) {
            return BicycleDto.builder()
                    .id(bicycle.getId())
                    .number(bicycle.getNumber())
                    .model(bicycle.getModel())
                    .vin(bicycle.getVin())
                    .station(stationMapper.mapToDto(bicycle.getStation()))
                    .status(bicycle.getStatus())
                    .build();
        }
        return null;
    }

    @Override
    public List<BicycleDto> mapToListDto(List<Bicycle> bicycles) {
        if (Objects.nonNull(bicycles)) {
            List<BicycleDto> bicycleDtoList = new ArrayList<>();
            for (Bicycle bike : bicycles) {
                bicycleDtoList.add(mapToDto(bike));
            }
            return bicycleDtoList;
        }
        return Collections.emptyList();
    }

    @Override
    public Bicycle mapToEntity(BicycleDto bicycleDto) {
        if (Objects.nonNull(bicycleDto)) {
            Bicycle bicycle = Bicycle.builder()
                    .number(bicycleDto.getNumber())
                    .model(bicycleDto.getModel())
                    .vin(bicycleDto.getVin())
                    .station(stationMapper.mapToEntity(bicycleDto.getStation()))
                    .status(bicycleDto.getStatus())
                    .build();
            bicycle.setId(bicycleDto.getId());
            return bicycle;
        }
        return null;
    }
}
