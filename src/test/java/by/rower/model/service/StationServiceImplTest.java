package by.rower.model.service;


import by.rower.model.config.DatabaseConfigTest;
import by.rower.model.dto.BicycleDto;
import by.rower.model.dto.StationDto;
import by.rower.model.util.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.rower.model.util.TestDataImporter.LIMIT_10;
import static by.rower.model.util.TestDataImporter.OFFSET_0;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DatabaseConfigTest.class)
@Transactional(readOnly = true)
public class StationServiceImplTest {

    @Autowired
    private StationService stationService;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void initDb() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    void findAllSection() {
        List<StationDto> results = stationService.findAll(LIMIT_10, OFFSET_0);
        assertEquals(3, results.size());
    }
}
