package by.rower.model.dao;

import by.rower.model.config.DatabaseConfigTest;
import by.rower.model.entity.Bicycle;
import by.rower.model.entity.Order;
import by.rower.model.util.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static by.rower.model.util.TestDataImporter.LIMIT_10;
import static by.rower.model.util.TestDataImporter.OFFSET_0;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DatabaseConfigTest.class)
@Transactional(readOnly = true)
class OrderDaoImplTest {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void initDb() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    void testFindAll() {
        List<Order> results = orderDao.findAll(LIMIT_10, OFFSET_0);
        assertEquals(3, results.size());
    }

    @Test
    void testFindById() {
        Optional<Order> byId = orderDao.findById(1L);
        byId.ifPresent(order -> assertEquals(2, order.getBicycle()));
    }

    @Test
    @Transactional
    void testAdd() {
        Order order = Order.builder().bicycle(null)
                .account(null).rentalTime(LocalDateTime.now()).rentalPeriod(LocalDateTime.MAX).build();
        Long id = orderDao.save(order);
        Optional<Order> byId = orderDao.findById(id);
        assertTrue(byId.isPresent());
        orderDao.delete(byId.get());
    }

    @Test
    @Transactional
    void testUpdate() {
        Bicycle bicycle = Bicycle.builder().build();
        bicycle.setId(1L);
        Optional<Order> byId = orderDao.findById(1L);
        byId.ifPresent(order -> {
            order.setBicycle(bicycle);
            orderDao.update(order);
        });
        Optional<Order> updatedOrder = orderDao.findById(1L);
        updatedOrder.ifPresent(order -> assertEquals(1, order.getBicycle()));
    }

    @Test
    @Transactional
    void testDelete() {
        List<Order> allOrder = orderDao.findAll(LIMIT_10, OFFSET_0);
        orderDao.delete(allOrder.get(1));
        List<Order> orders = orderDao.findAll(LIMIT_10, OFFSET_0);
        assertEquals(2, orders.size());
    }

    @Test
    void findAllByUsername() {
        List<Order> byUsername = orderDao.findAllByUsername("user", LIMIT_10, OFFSET_0);
        assertEquals(1, byUsername.size());
    }
}