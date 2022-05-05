package by.rower.web.controller;


import by.rower.model.dto.user.AccountDto;
import by.rower.model.entity.user.Admin;
import by.rower.model.entity.user.User;
import by.rower.model.service.AccountService;
import by.rower.web.util.LoggerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

import static by.rower.web.util.PageUtil.*;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SessionAttributes({ACCOUNT_ATTRIBUTE})
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String indexPage() {
        return INDEX_PAGE_SUFFIX;
    }

    @GetMapping("/about")
    public String aboutPage() {
        return ABOUT_PAGE_PREFIX;
    }
    @GetMapping("/price")
    public String pricePage() {
        return PRICE_PAGE_PREFIX;
    }

    @GetMapping("/login")
    public String loginPage() {
        return LOGIN_PAGE_SUFFIX;
    }

    @GetMapping("/registerPage")
    public String registerPage(Model model) {
        model.addAttribute(ACCOUNT_ATTRIBUTE, AccountDto.builder().build());
        return REGISTER_PAGE_SUFFIX;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute(ACCOUNT_ATTRIBUTE) @Valid AccountDto account,
                           Errors errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return REGISTER_PAGE_SUFFIX;
        }
        if (Objects.nonNull(account.getUsername()) && Objects.nonNull(account.getPassword())) {
            Optional<AccountDto> accountByUsername = accountService.findAccountByUsername(account.getUsername());
            if (accountByUsername.isEmpty()) {
                String rawPassword = account.getPassword();
                account.setPassword(passwordEncoder.encode(rawPassword));
                accountService.saveUser(account);
                log.debug(LoggerUtil.ENTITY_WAS_SAVED_IN_CONTROLLER, account);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(account.getUsername(), rawPassword);
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                Authentication authentication = authenticationManager.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return REDIRECT + SUCCESS_PAGE;
            }
            return REGISTER_PAGE_SUFFIX;
        }
        return REGISTER_PAGE_SUFFIX;
    }

    @GetMapping("/success")
    public String success(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User account,
            Model model) {
        Optional<AccountDto> accountByUsername = accountService.findAccountByUsername(account.getUsername());
        if (accountByUsername.isPresent()) {
            AccountDto accountDto = accountByUsername.get();
            log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_CONTROLLER_BY, accountDto, account.getUsername());
            if (!accountDto.isBanned()) {
                model.addAttribute(ACCOUNT_ATTRIBUTE, accountDto);
                switch (accountDto.getRole()) {
                    case USER_ROLE:
                        return REDIRECT + USER_PAGE;
                    case ADMIN_ROLE:
                        return REDIRECT + ADMIN_PAGE;
                    default:
                        return REDIRECT + ERROR_PAGE;
                }
            }
            return REDIRECT + BAN_PAGE;
        }
        return REDIRECT + ERROR_PAGE;
    }

    @GetMapping("/user/userPage")
    public String userPage() {
        return USER_PREFIX + USER_PAGE_SUFFIX;
    }

    @GetMapping("/admin/adminPage")
    public String adminPage() {
        return ADMIN_PREFIX + ADMIN_PAGE_SUFFIX;
    }

    @PostMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return REDIRECT + START_PAGE;
    }

    @GetMapping("/errorPage")
    public String errorPage() {
        return ERROR_PAGE_PREFIX;
    }

    @GetMapping("/banPage")
    public String banPage() {
        return BAN_PAGE_PREFIX;
    }

    @GetMapping("/user/showUserInfo")
    public String showUserInfo(@SessionAttribute(ACCOUNT_ATTRIBUTE) AccountDto account, Model model) {
        Optional<AccountDto> userByUsername = accountService.findAccountByUsername(account.getUsername());
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_CONTROLLER_BY, userByUsername, account.getUsername());
        userByUsername.ifPresent(accountDto -> model.addAttribute(ACCOUNT_ATTRIBUTE, accountDto));
        return USER_PREFIX + USER_INFO_PAGE_SUFFIX;
    }

    @GetMapping("/user/editUser")
    public String editUserPage(@SessionAttribute(ACCOUNT_ATTRIBUTE) AccountDto account, Model model) {
        Optional<AccountDto> userByUsername = accountService.findAccountByUsername(account.getUsername());
        log.debug(LoggerUtil.ENTITY_WAS_FOUND_IN_CONTROLLER_BY, userByUsername, account.getUsername());
        userByUsername.ifPresent(accountDto -> model.addAttribute(ACCOUNT_ATTRIBUTE, accountDto));
        return USER_PREFIX + EDIT_USER_SUFFIX;
    }

    @PostMapping("/editUser")
    public String editUser(Model model, @ModelAttribute(ACCOUNT_ATTRIBUTE) @Valid AccountDto account, Errors errors) {
        if (errors.hasErrors()) {
            return USER_PREFIX + EDIT_USER_SUFFIX;
        }
        if (Objects.nonNull(account.getUsername()) && Objects.nonNull(account.getPassword())) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));

            accountService.updateUser(account);
            log.debug(LoggerUtil.ENTITY_WAS_UPDATED_IN_CONTROLLER, account);
            model.addAttribute(ACCOUNT_ATTRIBUTE, account);
            return REDIRECT + USER_PAGE;
        } else {
            return REDIRECT + ERROR_PAGE;
        }
    }

    @GetMapping("/admin/allUsersPage")
    public String allUsersPage(Model model,
                               @RequestParam(name = OFFSET_PARAMETER, defaultValue = VALUE_ZERO) String offset) {
        model.addAttribute(ALL_USERS_ATTRIBUTE, accountService.findAllUsers(LIMIT_TEN, Integer.parseInt(offset)));
        model.addAttribute(COUNT_PAGES_ATTRIBUTE, accountService.getCountPages(User.class));
        return ADMIN_PREFIX + ALL_USERS_SUFFIX;
    }

    @GetMapping("/admin/allAdminsPage")
    public String allAdminsPage(Model model,
                                @RequestParam(name = OFFSET_PARAMETER, defaultValue = VALUE_ZERO) String offset) {
        model.addAttribute(ALL_ADMINS_ATTRIBUTE, accountService.findAllAdmins(LIMIT_TEN, Integer.parseInt(offset)));
        model.addAttribute(COUNT_PAGES_ATTRIBUTE, accountService.getCountPages(Admin.class));
        return ADMIN_PREFIX + ALL_ADMINS_SUFFIX;
    }

    @GetMapping("/admin/blockUserPage")
    public String blockUserPage(Model model) {
        model.addAttribute(DEBTORS_ATTRIBUTE, accountService.findAllDebtors());
        return ADMIN_PREFIX + BLOCK_USER_PAGE;
    }

    @PostMapping("/blockUser")
    public String blockUser(@RequestParam(USERNAME_PARAMETER) String username) {
        Optional<AccountDto> userByUsername = accountService.findAccountByUsername(username);
        if (userByUsername.isPresent()) {
            accountService.blockUser(userByUsername.get());
            log.debug(LoggerUtil.USER_WAS_BLOCKED_IN_CONTROLLER, userByUsername.get());
            return REDIRECT + ADMIN_PAGE;
        }
        return REDIRECT + ERROR_PAGE;
    }

    @GetMapping("/admin/addEmployeePage")
    public String addEmployeePage(Model model) {
        model.addAttribute(ACCOUNT_ATTRIBUTE, AccountDto.builder().build());
        return ADMIN_PREFIX + ADD_EMPLOYEE_SUFFIX;
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute(ACCOUNT_ATTRIBUTE) @Valid AccountDto account, Errors errors) {
        if (errors.hasErrors()) {
            return ADMIN_PREFIX + ADD_EMPLOYEE_SUFFIX;
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if (Objects.equals(ADMIN_ROLE, account.getRole())) {
            accountService.saveAdmin(account);
            log.debug(LoggerUtil.ENTITY_WAS_SAVED_IN_CONTROLLER, account);
            return REDIRECT + ADMIN_PAGE;
        } else {
            return REDIRECT + ERROR_PAGE;
        }
    }

    @GetMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable(ID_PATH_VARIABLE) String id, Model model) {
        Optional<AccountDto> userById = accountService.findById(Long.parseLong(id));
        userById.ifPresent(user -> {
            if (user.getOrdersAmount() == 0) {
                accountService.delete(user);
                log.debug(LoggerUtil.ENTITY_WAS_DELETED_IN_CONTROLLER, user);
            } else {
                model.addAttribute(CANNOT_DELETE_ATTRIBUTE, true);
            }
        });
        return REDIRECT + ALL_USERS_PAGE;
    }
}
