package by.rower.model.dao;


import by.rower.model.config.DatabaseConfigTest;
import by.rower.model.entity.user.Account;
import by.rower.model.entity.user.Admin;
import by.rower.model.entity.user.User;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DatabaseConfigTest.class)
@Transactional(readOnly = true)
class AccountDaoImplTest {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void initDb() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    void findAll() {
        List<Account> results = accountDao.findAll(LIMIT_10, OFFSET_0);
        assertEquals(6, results.size());
    }

    @Test
    void findAllUsers() {
        List<User> results = accountDao.findAllUsers(LIMIT_10, OFFSET_0);
        assertEquals(2, results.size());
    }

    @Test
    void findAllAdmins() {
        List<Admin> results = accountDao.findAllAdmins(LIMIT_10, OFFSET_0);
        assertEquals("admin", results.get(0).getUsername());
    }

    @Test
    void findByUsername() {
        Optional<Account> results = accountDao.findByUsername("user");
        results.ifPresent(user -> assertEquals("user", user.getUsername()));
    }

    @Test
    @Transactional
    void add() {
        accountDao.save(User.builder().username("superman").password("1234").build());
        Optional<Account> results = accountDao.findByUsername("superman");
        assertTrue(results.isPresent());
    }

    @Test
    @Transactional
    void update() {
        Optional<Account> byId = accountDao.findById(3L);
        byId.ifPresent(account -> {
            account.setUsername("user2");
            accountDao.update(account);
        });
        Optional<Account> updatedAccount = accountDao.findById(3L);
        updatedAccount.ifPresent(account -> assertEquals("user2", account.getUsername()));
    }

    @Test
    void findUserById() {
        Optional<Account> userById = accountDao.findById(1L);
        userById.ifPresent(user -> {
            assertEquals("user", user.getUsername());
            assertEquals("user", user.getRole());
        });
    }

    @Test
    void findAllDebtors() {
        List<User> allDebtors = accountDao.findAllDebtors();
        assertEquals(0, allDebtors.size());
    }
}