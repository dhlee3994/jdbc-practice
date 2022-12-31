package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

class UserDaoTest {

    @BeforeEach
    void setUp() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db_schema.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @DisplayName("유저 생성 테스트")
    @Test
    void create() throws Exception {
        final UserDao userDao = new UserDao();
        final User userRequest = new User("dhlee", "password", "이동현", "dhlee3994@gmail.com");
        userDao.create(userRequest);

        User user = userDao.findByUserId("dhlee");
        assertEquals(user, userRequest);
    }
}
