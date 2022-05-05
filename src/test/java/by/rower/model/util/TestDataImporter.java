package by.rower.model.util;


import by.rower.model.entity.Bicycle;
import by.rower.model.entity.Order;
import by.rower.model.entity.Station;
import by.rower.model.entity.user.Admin;
import by.rower.model.entity.user.BicycleStatus;
import by.rower.model.entity.user.Level;
import by.rower.model.entity.user.User;
import by.rower.model.entity.user.UserData;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Component("testDataImporter")
public class TestDataImporter {

    public static final int LIMIT_10 = 10;
    public static final int OFFSET_0 = 0;
    private final SessionFactory sessionFactory;

    @Autowired
    public TestDataImporter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void importTestData() {
        Session session = sessionFactory.getCurrentSession();

        Bicycle aistBicycle = saveBicycle(session,4579, "aist", 123465, BicycleStatus.FREE);
        Bicycle stelsBicycle = saveBicycle(session,3279, "stels",987456, BicycleStatus.ORDER);
        Bicycle ltdBicycle = saveBicycle(session,1111, "LTD",147896, BicycleStatus.FREE);
        Bicycle ferBicycle = saveBicycle(session,1181, "FRE",177796, BicycleStatus.SERVICE);

        Station station1 = saveStation(session, 11, 25);
        Station station2 = saveStation(session, 22, 10);
        Station station3 = saveStation(session, 33, 30);


        User user = saveUser(session, "user", "password", "Vasia", "Vermut",
                "miush@gmail.com", "+451244161451");
        User secondUser = saveUser(session, "user2", "123456", "Alex", "Pirogov",
                "joash@mail.com", "+370125711918");
        User thirdUser = saveUser(session, "user3", "123456", "Fedor", "Mamon",
                "momoash@mail.com", "+370123311918");
        saveAdmin(session, "admin", "admin", "Dima", "Ivanov",
                "erecfsc@mail.ru", "+451216161451", Level.HEAD);
        saveAdmin(session, "admin2", "admin2", "Mitiai", "Front",
                "vevdma@mail.com", "+851216161451", Level.SECONDARY);

        saveOrder(session, user,aistBicycle, LocalDateTime.now().plusDays(3));
        saveOrder(session, secondUser, ltdBicycle, LocalDateTime.now().plusDays(10));
        saveOrder(session, thirdUser, ferBicycle, LocalDateTime.now().plusDays(5));
    }

    public void cleanTestData() {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from User ").executeUpdate();
        session.createQuery("delete from Admin ").executeUpdate();
        session.createQuery("delete from Order ").executeUpdate();
        session.createQuery("delete from Bicycle ").executeUpdate();
        session.createQuery("delete from Station ").executeUpdate();
    }

    private static Station saveStation(Session session, long number, int capacity) {
        Station station = Station.builder().number(number).capacity(capacity).build();
        session.save(station);
        return station;
    }

    private static Bicycle saveBicycle(Session session, long number, String model, int vin, BicycleStatus status) {
        Bicycle bicycle = Bicycle.builder().number(number).model(model).vin(vin).status(status).build();
        session.save(bicycle);
        return bicycle;
    }

    private static User saveUser(Session session, String username, String password,
                                 String name, String surname, String email, String phone) {
        User user = User.builder().username(username).password(password).role("user")
                .userData(UserData.builder().name(name).surname(surname)
                        .emailAddress(email).phoneNumber(phone).build()).isBanned(false).build();
        session.save(user);
        return user;
    }

    private static void saveAdmin(Session session, String username, String password,
                                  String name, String surname, String email, String phone, Level level) {
        Admin admin = Admin.builder().username(username).password(password)
                .userData(UserData.builder().name(name).surname(surname)
                        .emailAddress(email).phoneNumber(phone).build()).adminLevel(level).build();
        session.save(admin);
    }


    private static void saveOrder(Session session, User account, Bicycle bicycle, LocalDateTime period) {
        Order order = Order.builder().account(account)
                .bicycle(bicycle).rentalTime(LocalDateTime.now()).rentalPeriod(period).build();
        session.save(order);
    }
}
