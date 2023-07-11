package com.agroniks.marketplace.item;

import com.agroniks.marketplace.item.jpa.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemAuthorizationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;


    @Test
    void shouldNotReturnAnythingWithoutCreds() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/api/v1/item", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldAuthorizeUserWithRoleCommon() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("Admin", "Zaq12wsx")
                .getForEntity("/api/v1/item", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }



}
