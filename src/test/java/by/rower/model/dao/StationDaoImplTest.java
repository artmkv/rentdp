package by.rower.model.dao;


import by.rower.model.config.DatabaseConfigTest;
import by.rower.model.entity.Station;
import by.rower.model.util.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static by.rower.model.util.TestDataImporter.LIMIT_10;
import static by.rower.model.util.TestDataImporter.OFFSET_0;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DatabaseConfigTest.class)
@Transactional(readOnly = true)
public class StationDaoImplTest {


    @Autowired
    private StationDao stationDao;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void initDb() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    void findAll() {
        List<Station> results = stationDao.findAll(LIMIT_10, OFFSET_0);
        assertEquals(3, results.size());
    }
}
