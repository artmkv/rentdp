package by.rower.model.dao;


import by.rower.model.entity.user.Account;
import by.rower.model.entity.user.Admin;
import by.rower.model.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface AccountDao extends GenericDao<Long, Account> {

    Optional<Account> findByUsername(String username);

    void blockUser(User user);

    void unblockUser(User user);

    List<User> findAllDebtors();

    List<User> findAllUsers(int limit, int offset);

    List<Admin> findAllAdmins(int limit, int offset);

    Long getCountRow(Class<? extends Account> clazz);
}

