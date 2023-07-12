package com.agroniks.marketplace.user;

import com.agroniks.marketplace.user.jpa.UserCommand;
import com.agroniks.marketplace.user.jpa.UserEntityRepository;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserEndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserEntityService userService;

    @Autowired
    private UserEntityRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        List<UserCommand> userList = new ArrayList<>();
        userList.add(new UserCommand("USER1", null, 10.0, "USER1_LOGIN", "USER1_PASS", null));
        userList.add(new UserCommand("USER2", null, 20.0, "USER1_LOGIN", "USER1_PASS", null));
        userList.add(new UserCommand("USER3", null, 30.0, "USER1_LOGIN", "USER1_PASS", null));
        userList.add(new UserCommand("Admin", null, 100.0, "Admin", "Zaq12wsx", null));
        userList.forEach(userCommand -> userService.save(userCommand));
    }

    @Test
    void ShouldReturnFirstPageWithTwoEntities() {
        ResponseEntity<List> response = restTemplate
                .withBasicAuth("Admin", "Zaq12wsx")
                .getForEntity("/api/v1/users?page=0&size=2", List.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
    }

    @Test
    @DirtiesContext
    void shouldAddNewUserToDbAndReturnIdOfHim() {
        UserCommand userCommand = new UserCommand("USER4", null, 40.0, "USER4_LOGIN", "USER4_PASS", null);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserCommand> request = new HttpEntity<>(userCommand, headers);

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("Admin", "Zaq12wsx")
                .postForEntity("/api/v1/users", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var locationHeader = response.getHeaders().getLocation();
        String idOfNewUser = StringUtils.substringAfterLast(locationHeader.toString(), "/");
        System.out.println("ID: " + idOfNewUser);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("Admin", "Zaq12wsx")
                .getForEntity("/api/v1/users/" + idOfNewUser, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        String name = documentContext.read("$.name");
        assertThat(name).isEqualTo("USER4");
    }


}
