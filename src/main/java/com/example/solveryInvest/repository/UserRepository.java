package com.example.solveryInvest.repository;

import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.entity.User;
import com.example.solveryInvest.entity.enums.Role;
import jooq.tables._User;
import jooq.tables.records._UserRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.EnumConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

import static jooq.tables._User._USER;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private Connection connection;

    private static final EnumConverter<String, Role> roleConverter = new EnumConverter<>(String.class, Role.class);

    @Value("${jooq.username}")
    private String username;

    @Value("${jooq.password}")
    private String password;

    @Value("${jooq.url}")
    private String url;

    public Optional<User> findByEmailAndPassword(String email, String password) {
        try {
            DSLContext context = DSL.using(getConnection(), SQLDialect.POSTGRES);
            jooq.tables.records._UserRecord userRecord = context.fetchOne(_USER, _USER.EMAIL.eq(email), _USER.PASSWORD.eq(password));
            if (Objects.nonNull(userRecord)) {
                var user = User.builder()
                        .id(userRecord.getValue(_USER.ID))
                        .firstName(userRecord.getValue(_USER.FIRST_NAME))
                        .lastName(userRecord.getValue(_USER.LAST_NAME))
                        .email(userRecord.getValue(_USER.EMAIL))
                        .role(roleConverter.from(userRecord.getValue(_USER.ROLE)))
                        .password(userRecord.getValue(_USER.PASSWORD))
                        .build();
                return Optional.of(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        try {
            DSLContext context = DSL.using(getConnection(), SQLDialect.POSTGRES);
            jooq.tables.records._UserRecord userRecord = context.fetchOne(_USER, _USER.EMAIL.eq(email));
            if (Objects.nonNull(userRecord)) {
                var user = User.builder()
                        .id(userRecord.getValue(_USER.ID))
                        .firstName(userRecord.getValue(_USER.FIRST_NAME))
                        .lastName(userRecord.getValue(_USER.LAST_NAME))
                        .email(userRecord.getValue(_USER.EMAIL))
                        .role(roleConverter.from(userRecord.getValue(_USER.ROLE)))
                        .password(userRecord.getValue(_USER.PASSWORD))
                        .build();
                return Optional.of(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public User save(User user) {
        try {
            DSLContext context = DSL.using(getConnection(), SQLDialect.POSTGRES);
            jooq.tables.records._UserRecord userRecord = context.newRecord(_USER);
            userRecord.setFirstName(user.getFirstName());
            userRecord.setLastName(user.getLastName());
            userRecord.setPassword(user.getPassword());
            userRecord.setEmail(user.getEmail());
            userRecord.setRole(roleConverter.to(Role.GUEST));
            userRecord.store();
            var id = userRecord.get(_USER.ID);
            user.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
