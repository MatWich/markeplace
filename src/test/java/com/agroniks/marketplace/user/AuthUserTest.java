package com.agroniks.marketplace.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthUserTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("If the user with only NORMAL_ROLE will try to get sth from" +
            " the path that requires ADMIN_ROLE should get 403 code")
    void shouldGet403IfUserDoesNotHaveAdminRole() {

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("SBob", "SBob_pass")
                .getForEntity("/api/v1/users", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("User with NORMAL_ROLE should be able to get anything from items path")
    void shouldReturnCodeOKIfUserTryToGetSthFromItemsPath() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("SBob", "SBob_pass")
                .getForEntity("/api/v1/item", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("User should get unauthorized when trying to authenticate with no creds")
    void shouldGet401WhenCredentialsAreNotPassedInQuery() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/item", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

    }
}
