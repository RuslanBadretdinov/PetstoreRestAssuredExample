package io;

import com.github.javafaker.Faker;
import io.petstore.dto.rp.RPCodeTypeMessageDTO;
import io.petstore.services.pet.PetServiceApi;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class ExampleTest {
    private Faker faker = new Faker();
    private PetServiceApi petServiceApi = new PetServiceApi();

    @Test
    @Tag("@test1")
    public void test1() {
        // Проверка полей №1
        ValidatableResponse petRs = petServiceApi.postPetViaForm(5L, faker.name().firstName(), "sold");

        petRs.statusCode(200);

        petRs
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", equalTo(Long.toString(5L)));
    }

    @Test
    @Tag("@test2")
    public void test2() {
        // Проверка полей с помощью JsonPath
        ValidatableResponse petRs = petServiceApi.postPetViaForm(5L, faker.name().firstName(), "sold");

        petRs.statusCode(200);

        Long actualCode = petRs.extract().body().jsonPath().get("code");
        Long expectedCode = 200L;

        Assertions.assertEquals(expectedCode, actualCode, "Code is incorrect");

        // Чтобы вытащить лист элементов:
        List<Long> actualCodeList = petRs.extract().body().jsonPath().getList("code");

//        // Пример JsonPath без extract
//        petRs.body("store.book.category", equalTo(200));
    }

    @Test
    @Tag("@test3")
    public void test3() {
        // Проверка полей body с помощью dto объекта
        ValidatableResponse petRs = petServiceApi.postPetViaForm(5L, faker.name().firstName(), "sold");

        petRs.statusCode(200);

        RPCodeTypeMessageDTO rPCodeTypeMessageDTO = petRs.extract().body().as(RPCodeTypeMessageDTO.class);

        Assertions.assertAll("rsCodeTypeMessageDTO",
                () -> Assertions.assertEquals(200L, rPCodeTypeMessageDTO.getCode()),
                () -> Assertions.assertEquals("unknown", rPCodeTypeMessageDTO.getType()),
                () -> Assertions.assertEquals(Long.toString(5L), rPCodeTypeMessageDTO.getMessage())
        );
    }

    @Test
    @Tag("@test4")
    public void test4() {
        // Валидация JSON схемы
        ValidatableResponse petRs = petServiceApi.postPetViaForm(5L, faker.name().firstName(), "sold");

        petRs.statusCode(200);
        petRs.body(matchesJsonSchemaInClasspath("schema/rs/RSCodeTypeMessageSchema.json"));

        RPCodeTypeMessageDTO rPCodeTypeMessageDTO = petRs.extract().body().as(RPCodeTypeMessageDTO.class);

        Assertions.assertAll("rpCodeTypeMessageDTO",
                () -> Assertions.assertEquals(200L, rPCodeTypeMessageDTO.getCode()),
                () -> Assertions.assertEquals("unknown", rPCodeTypeMessageDTO.getType()),
                () -> Assertions.assertEquals(Long.toString(5L), rPCodeTypeMessageDTO.getMessage())
        );
    }
}