package by.rower.model.service;


import by.rower.model.dto.user.AccountDto;
import by.rower.model.entity.user.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface AccountService extends GenericService<AccountDto, Long, Account>, UserDetailsService {

    List<AccountDto> findAllUsers(int limit, int offset);

    List<Long> getCountPages(Class<? extends Account> clazz);

    List<AccountDto> findAllDebtors();

    List<AccountDto> findAllAdmins(int limit, int offset);

    Optional<AccountDto> findAccountByUsername(String username);

    void blockUser(AccountDto userDto);

    void unblockUser(AccountDto userDto);

    Long saveUser(AccountDto userDto);

    Long saveAdmin(AccountDto adminDto);

    void updateUser(AccountDto userDto);

    void updateAdmin(AccountDto adminDto);
}

