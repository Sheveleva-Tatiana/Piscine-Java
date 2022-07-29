package school21.spring.service.services;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersServiceImplTest {
    private static DataSource dataSource;
    private static UsersService usersServiceJdbc;
    private static UsersService usersServiceJdbcTemplate;

    @BeforeAll
    static void before() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        dataSource = context.getBean("dataSource", DataSource.class);
        usersServiceJdbc = context.getBean("usersServiceJdbc", UsersService.class);
        usersServiceJdbcTemplate = context.getBean("usersServiceJdbcTemplate", UsersService.class);
    }


    @BeforeEach
    private void init() {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("drop schema if exists models cascade;");
            st.executeUpdate("create schema if not exists models;");
            st.executeUpdate("create table if not exists models.user "
                    + "(id integer, email varchar(50) not null, password varchar(50));");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"user1@school21.ru", "user2@school21.ru", "user3@school21.ru"})
    public void isSignedUp(String email) {
        assertNotNull(usersServiceJdbc.signUp(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"user1@school21.ru", "user2@school21.ru", "user3@school21.ru"})
    public void isSignedUpTemplate(String email) {
        assertNotNull(usersServiceJdbcTemplate.signUp(email));
    }
}