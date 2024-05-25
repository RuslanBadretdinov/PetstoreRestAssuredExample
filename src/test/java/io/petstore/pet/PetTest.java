package io.petstore.pet;

import com.github.javafaker.Faker;
import io.petstore.services.pet.PetServiceApi;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class PetTest {
    private Faker faker = new Faker();
    private PetServiceApi petServiceApi = new PetServiceApi();

    @Test
    @Tag("@test1")
    public void test() {
        ValidatableResponse petRs = petServiceApi.postPetViaForm(5, faker.name().firstName(), "sold");
        petRs.statusCode(200);
    }
}