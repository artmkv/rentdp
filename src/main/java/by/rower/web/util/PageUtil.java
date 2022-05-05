package by.rower.web.util;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public final class PageUtil {

    private PageUtil() {
        throw new UnsupportedOperationException();
    }

    public static final String USER_PREFIX = "page/user/";
    public static final String ADMIN_PREFIX = "page/admin/";
    public static final String LOGIN_PAGE = "/login";
    public static final String ERROR_PAGE_PREFIX = "page/errorPage";
    public static final String BAN_PAGE_PREFIX = "page/banPage";
    public static final String BAN_PAGE = "banPage";

    public static final String PRICE_PAGE_PREFIX = "page/pricePage";
    public static final String PRICE_PAGE = "price";
    public static final String PRICE_PAGE_SUFFIX = "price";
    public static final String ABOUT_PAGE_PREFIX = "page/aboutPage";
    public static final String ABOUT_PAGE = "about";
    public static final String ABOUT_PAGE_SUFFIX = "about";
    public static final double PAY_IN_DAY = 12.5;

    public static final String START_PAGE = "/";
    public static final String USER_ATTRIBUTE = "user";
    public static final String ACCOUNT_ATTRIBUTE = "account";
    public static final String BICYCLE_ATTRIBUTE = "bicycle";
    public static final String STATION_ATTRIBUTE = "station";
    public static final String COUNT_PAGES_ATTRIBUTE = "countPages";
    public static final String OFFSET_PARAMETER = "offset";
    public static final String ALL_USERS_ATTRIBUTE = "allUsers";
    public static final String ALL_USERS_SUFFIX = ALL_USERS_ATTRIBUTE;
    public static final String ALL_ADMINS_ATTRIBUTE = "allAdmins";
    public static final String CANNOT_DELETE_ATTRIBUTE = "cannotDel";
    public static final String ALL_ADMINS_SUFFIX = ALL_ADMINS_ATTRIBUTE;
    public static final String VALUE_ZERO = "0";

    public static final String ID_PATH_VARIABLE = "id";
    public static final String ALL_BICYCLES_PAGE = "allBicycles";
    public static final String ALL_BICYCLES_ATTRIBUTE = ALL_BICYCLES_PAGE;
    public static final String ALL_BICYCLES_USER_ATTRIBUTE = "userAllBicycles";
    public static final String BICYCLE_ID_PARAMETER = "bicycleId";
    public static final String EDIT_BICYCLE_SUFFIX = "editBicycle";
    public static final String ADD_BICYCLE_SUFFIX = "addBicycle";
    public static final String ALL_BICYCLES_USER_PAGE = "user/userAllBicycles";
    public static final String ALL_BICYCLES_ADMIN_PAGE = "admin/allBicycles";
    public static final String SHOW_ALL_BICYCLES_USER_PAGE = "userAllBicycles";



    public static final String ALL_STATIONS_PAGE = "allStations";
    public static final String ALL_STATIONS_ATTRIBUTE = "allStations";
    public static final String STATION_ID_PARAMETER = "stationId";
    public static final String EDIT_STATION_SUFFIX = "editStation";
    public static final String ADD_STATION_SUFFIX = "addStation";
    public static final String ALL_STATIONS_USER_PAGE = "userAllStations";

    public static final String ALL_ORDERS_ATTRIBUTE = "allOrders";
    public static final String ADD_EMPLOYEE_SUFFIX = "addEmployee";
    public static final String DEBTORS_ATTRIBUTE = "debtors";
    public static final String USER_PAGE_SUFFIX = "user";
    public static final String USER_ROLE = USER_PAGE_SUFFIX;
    public static final String SUCCESS_PAGE = "/success";
    public static final String USER_PAGE = "user/userPage";
    public static final String ORDER_BLANC_PAGE = "userOrderBlanc";
    public static final String ORDER_BLANC_ATTRIBUTE = "userOrderBlanc";
    public static final String ADMIN_PAGE = "admin/adminPage";
    public static final String REGISTER_PAGE = "registerPage";
    public static final String ERROR_PAGE = "errorPage";
    public static final String INDEX_PAGE_SUFFIX = "index";
    public static final String LOGIN_PAGE_SUFFIX = "login";
    public static final String RENTAL_PERIOD_PARAMETER = "rentalPeriod";
    public static final String REDIRECT = "redirect:";
    public static final String USERNAME_PARAMETER = "username";
    public static final String BLOCK_USER_PAGE = "blockUser";
    public static final String ADMIN_PAGE_SUFFIX = "admin";
    public static final String REGISTER_PAGE_SUFFIX = "register";
    public static final String ADMIN_ROLE = ADMIN_PAGE_SUFFIX;
    public static final String EDIT_USER_SUFFIX = "editUser";
    public static final String USER_ORDER_PAGE = "userOrder";
    public static final String USER_ORDER_ATTRIBUTE = "userOrder";
    public static final String EXCEPTION_MESSAGE = "exceptionMessage";
    public static final String ALL_STATUS_ATTRIBUTE = "allStatus";
    public static final String ALL_USERS_PAGE = "/admin/allUsersPage";
    public static final String USER_INFO_PAGE_SUFFIX = "userInfo";
    public static final int OFFSET_ZERO = 0;
    public static final int LIMIT_TEN = 10;

    public static LocalDateTime setRentalPeriod(String period) {
        LocalDateTime now = LocalDateTime.now();
        int rentalPeriod = Integer.parseInt(period);
            return now.plusDays(rentalPeriod);
    }
}

