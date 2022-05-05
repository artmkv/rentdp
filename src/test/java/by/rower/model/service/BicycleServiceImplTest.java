package by.rower.model.service;

import by.rower.model.config.DatabaseConfigTest;
import by.rower.model.dto.BicycleDto;
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
public class BicycleServiceImplTest {

    @Autowired
    private BicycleService bicycleService;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void initDb() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    void findAllSection() {
        List<BicycleDto> results = bicycleService.findAll(LIMIT_10, OFFSET_0);
        assertEquals(3, results.size());
    }

    @Test
    void addNewSection() {
        bicycleService.save(BicycleDto.builder().number(112233).build());
        Optional<BicycleDto> plants = bicycleService.findByNumber(112233);
        assertTrue(plants.isPresent());
    }

    @Test
    void findBicycleById() {
        Optional<BicycleDto> sectionById = bicycleService.findById(2L);
        sectionById.ifPresent(sectionDto -> assertEquals("stels", sectionDto.getModel()));
    }

    @Test
    void findBicycleByName() {
        Optional<BicycleDto> sectionByName = bicycleService.findByNumber(55555);
        assertFalse(sectionByName.isPresent());
    }

    @Test
    void updateBicycle() {
        Optional<BicycleDto> byName = bicycleService.findByNumber(1111);
        byName.ifPresent(section -> {
            section.setModel("fiat");
            bicycleService.update(section);
        });
        Optional<BicycleDto> updatedSection = bicycleService.findById(3L);
        updatedSection.ifPresent(planets -> assertEquals("fiat", planets.getModel()));
    }

    @Test
    void deleteBicycle() {
        Optional<BicycleDto> byId = bicycleService.findById(2L);
        byId.ifPresent(section -> bicycleService.delete(section));
        Optional<BicycleDto> sectionById = bicycleService.findById(2L);
        assertFalse(sectionById.isPresent());
    }

}
