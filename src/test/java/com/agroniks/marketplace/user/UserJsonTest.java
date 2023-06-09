package com.agroniks.marketplace.user;

import com.agroniks.marketplace.user.jpa.UserEntity;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserJsonTest {

    @Autowired
    JacksonTester<UserEntity> json;

    @Test
    void userSerializationTest() throws IOException {
        UserEntity userEntity = new UserEntity("USER1", 20.0, "USER1_LOGIN", "USER1_PASS");
        assertThat(json.write(userEntity)).isStrictlyEqualToJson("user.json");

        assertThat(json.write(userEntity)).hasJsonPathStringValue("@.name");
        assertThat(json.write(userEntity)).extractingJsonPathStringValue("@.name")
                .isEqualTo("USER1");

        assertThat(json.write(userEntity)).hasJsonPathNumberValue("@.money");
        assertThat(json.write(userEntity)).extractingJsonPathNumberValue("@.money")
                .isEqualTo(20.0);

        assertThat(json.write(userEntity)).hasJsonPathArrayValue("@.items");
    }

    @Test
    void userDeserializationTest() throws IOException {
        String expected = """
                {
                  "id": null,
                  "name": "USER1",
                  "money": 20.0,
                  "items": [],
                  "username": "USER1_LOGIN",
                  "password": "USER1_PASS",
                  "roles": ["NORMAL"]
                }
                """;

        List<String> roles =  new ArrayList<>();
        roles.add("NORMAL");
        assertThat(json.parse(expected))
                .isEqualTo(new UserEntity("USER1", null,20.0, "USER1_LOGIN", "USER1_PASS", roles));

        assertThat(json.parseObject(expected).getId()).isEqualTo(null);
        assertThat(json.parseObject(expected).getName()).isEqualTo("USER1");
        assertThat(json.parseObject(expected).getMoney()).isEqualTo(20.0);
        assertThat(json.parseObject(expected).getItems()).isEqualTo(new ArrayList<>());
    }

}
