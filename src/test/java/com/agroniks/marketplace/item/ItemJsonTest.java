package com.agroniks.marketplace.item;

import com.agroniks.marketplace.item.jpa.ItemEntity;
import com.agroniks.marketplace.item.jpa.ItemInfoEntity;
import com.agroniks.marketplace.user.jpa.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;


import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemJsonTest {

    @Autowired
    JacksonTester<ItemEntity> json;

    @Autowired
    JacksonTester<ItemInfoEntity> jsonForInfo;

    @Test
    void itemSerializationTest() throws IOException {
        ItemEntity itemEntity = new ItemEntity(null, "Item 1", "Item 1", 1.1);
        assertThat(json.write(itemEntity)).isStrictlyEqualToJson("item.json");

        assertThat(json.write(itemEntity)).hasEmptyJsonPathValue("@.id");

        assertThat(json.write(itemEntity)).hasJsonPathStringValue("@.name");
        assertThat(json.write(itemEntity)).extractingJsonPathStringValue("@.name")
                .isEqualTo("Item 1");

        assertThat(json.write(itemEntity)).hasJsonPathStringValue("@.description");
        assertThat(json.write(itemEntity)).extractingJsonPathStringValue("@.description")
                .isEqualTo("Item 1");

        assertThat(json.write(itemEntity)).hasJsonPathNumberValue("@.worth");
        assertThat(json.write(itemEntity)).extractingJsonPathNumberValue("@.worth")
                .isEqualTo(1.1);

        assertThat(json.write(itemEntity)).hasJsonPathArrayValue("@.info");
    }

    @Test
    void itemDeserializationTest() throws IOException {
        String expected = """
                {
                    "id": null,
                    "name": "Item 1",
                    "description": "Item 1",
                    "worth": 1.1,
                    "info": []
                }
                """;

        assertThat(json.parse(expected)).isEqualTo(new ItemEntity(null, "Item 1", "Item 1", 1.1));

        assertThat(json.parseObject(expected).getId()).isEqualTo(null);

        assertThat(json.parseObject(expected).getName()).isEqualTo("Item 1");

        assertThat(json.parseObject(expected).getDescription()).isEqualTo("Item 1");

        assertThat(json.parseObject(expected).getWorth()).isEqualTo(1.1);

        assertThat(json.parseObject(expected).getInfo()).isEqualTo(new HashSet<>());

    }

    @Test
    void itemInfoSerializationTest() throws IOException {
        ItemInfoEntity itemInfo = new ItemInfoEntity(null, 1, null, null);
        System.out.println(jsonForInfo.write(itemInfo));
        assertThat(jsonForInfo.write(itemInfo)).isStrictlyEqualToJson("iteminfo.json");

        assertThat(jsonForInfo.write(itemInfo)).hasJsonPathNumberValue("@.amount");
        assertThat(jsonForInfo.write(itemInfo)).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(1);
    }
}
