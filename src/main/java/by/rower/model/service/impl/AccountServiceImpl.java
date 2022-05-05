package by.rower.model.service.impl;


import by.rower.model.dao.AccountDao;
import by.rower.model.dto.user.AccountDto;
import by.rower.model.entity.user.Account;
import by.rower.model.entity.user.Admin;
import by.rower.model.entity.user.User;
import by.rower.model.mapper.impl.AccountMapper;
import by.rower.model.service.AccountService;
import by.rower.model.util.LoggerUtil;
import by.rower.model.util.ServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional(readOnly = true)
@Slf4j
public class AccountServiceImpl extends GenericServiceImpl<AccountDto, Long, Account> implements AccountService {

    private final AccountDao accountDao;
    private final AccountMapper accountMapper;
    private final Function<Account, UserDetails> userToUserDetails = user ->
            org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRole())
                    .build();

    @Autowired
    public AccountServiceImpl(AccountDao accountDao, AccountMapper accountMapper) {
        this.accountDao = accountDao;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<AccountDto> findAllUsers(int limit, int offset) {
        List<User> allUsers = accountDao.findAllUsers(limit, offset);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE, allUsers);
        return accountMapper.mapUserToListDto(allUsers);
    }

    @Override
    public List<Long> getCountPages(Class<? extends Account> clazz) {
        Long countRow = accountDao.getCountRow(clazz);
        log.debug(LoggerUtil.COUNT_ROW_WAS_FOUND_IN_SERVICE, countRow);
        return ServiceUtil.collectPages(countRow);
    }

    @Override
    public List<AccountDto> findAllDebtors() {
        List<User> allDebtors = accountDao.findAllDebtors();
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE, allDebtors);
        return accountMapper.mapUserToListDto(allDebtors);
    }

    @Override
    public List<AccountDto> findAllAdmins(int limit, int offset) {
        List<Admin> allAdmins = accountDao.findAllAdmins(limit, offset);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE, allAdmins);
        return accountMapper.mapAdminToListDto(allAdmins);
    }


    @Override
    public Optional<AccountDto> findAccountByUsername(String username) {
        Optional<Account> byUsername = accountDao.findByUsername(username);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, byUsername, username);
        return Optional.ofNullable(mapper.mapToDto(byUsername.orElse(null)));
    }

    @Override
    @Transactional
    public void blockUser(AccountDto userDto) {
        accountDao.blockUser((User) mapper.mapToEntity(userDto));
        log.debug(LoggerUtil.USER_WAS_BLOCKED_IN_SERVICE, userDto);
    }

    @Override
    @Transactional
    public void unblockUser(AccountDto userDto) {
        accountDao.unblockUser((User) mapper.mapToEntity(userDto));
        log.debug(LoggerUtil.USER_WAS_UNBLOCKED_IN_SERVICE, userDto);
    }

    @Override
    @Transactional
    public Long saveUser(AccountDto userDto) {
        Long saveUser = accountDao.save(mapper.mapToEntity(userDto));
        log.debug(LoggerUtil.ENTITY_WAS_SAVED_IN_SERVICE, saveUser);
        return saveUser;
    }

    @Override
    @Transactional
    public Long saveAdmin(AccountDto adminDto) {
        Long saveAdmin = accountDao.save(accountMapper.mapAdminToEntity(adminDto));
        log.debug(LoggerUtil.ENTITY_WAS_SAVED_IN_SERVICE, saveAdmin);
        return saveAdmin;
    }

    @Override
    @Transactional
    public void updateUser(AccountDto userDto) {
        accountDao.update(mapper.mapToEntity(userDto));
        log.debug(LoggerUtil.ENTITY_WAS_UPDATED_IN_SERVICE, userDto);
    }

    @Override
    @Transactional
    public void updateAdmin(AccountDto adminDto) {
        accountDao.update(accountMapper.mapAdminToEntity(adminDto));
        log.debug(LoggerUtil.ENTITY_WAS_UPDATED_IN_SERVICE, adminDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> maybeUser = accountDao.findByUsername(username);
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_SERVICE_BY, maybeUser, username);
        return maybeUser
                .map(userToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user with username: " + username));
    }
}
