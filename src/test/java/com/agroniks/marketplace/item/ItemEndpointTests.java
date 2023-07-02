package com.agroniks.marketplace.item;

import com.agroniks.marketplace.item.jpa.ItemCommand;
import com.agroniks.marketplace.item.jpa.ItemEntity;
import com.agroniks.marketplace.item.jpa.ItemRepository;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemEndpointTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();

        List<ItemCommand> itemList = new ArrayList<>();
        itemList.add(new ItemCommand("Item1", "Item 1", 1.1));
        itemList.add(new ItemCommand("Item2", "Item 2", 2.2));
        itemList.add(new ItemCommand("Item3", "Item 3", 3.3));

        itemList.forEach(itemEntity -> itemService.addNewItem(itemEntity));
    }

    /* GET MAPPINGS */

    @Test
    void shouldReturnOneItemFromDbInAscOrder() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/item?page=0&size=1,asc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number worth = documentContext.read("$.[0].worth");
        assertThat(worth).isEqualTo(1.1);

        String name = documentContext.read("$.[0].name");
        assertThat(name).isEqualTo("Item1");

        String desc = documentContext.read("$.[0].description");
        assertThat(desc).isEqualTo("Item 1");
    }

    @Test
    void shouldNotReturnAnItemWithUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/item/" + UUID.randomUUID().toString(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    @Test
    void shouldReturnAllItemsThatWorthIsLowerThan3() {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/v1/item/below/3", List.class);
        assertThat(response.getBody().size()).isEqualTo(2);
    }

    @Test
    void shouldReturnAllItemsThatWorthIsOver3() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/item/over/3", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        String name = documentContext.read("$.[0].name");
        assertThat(name).isEqualTo("Item3");
    }

    /* POST MAPPINGS */

    @Test
    @DirtiesContext
    void shouldReturnIdForNewlyCreatedItem() {
        ItemCommand itemCommand = new ItemCommand("Item4", "Item 4", 4.4);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ItemCommand> request = new HttpEntity<ItemCommand>(itemCommand, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/item", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var locationHeader = response.getHeaders().getLocation();
        String idOfNewItem = StringUtils.substringAfterLast(locationHeader.toString(), "/");

        ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/v1/item/" + idOfNewItem, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        String name = documentContext.read("$.name");
        assertThat(name).isEqualTo("Item4");
    }

    @Test
    void shouldThrowExceptionIfItemAlreadyExistsInDbWithTheSameName() {
        ItemCommand itemCommand = new ItemCommand("Item3", "Item3", 3.3);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ItemCommand> request = new HttpEntity<>(itemCommand, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/item", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
        assertThat(response.getBody()).isEqualTo("Item already in database");
    }

    /* DELETE MAPPINGS MAYBE THERE WILL BE SOME CONDITIONS? */

    /* PUT MAPPINGS */
}
