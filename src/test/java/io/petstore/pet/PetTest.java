package io.petstore.pet;

import com.github.javafaker.Faker;
import io.petstore.dto.inner_parts.Category;
import io.petstore.dto.inner_parts.TagDTO;
import io.petstore.dto.pet.PetDTO;
import io.petstore.dto.rp.RPCodeTypeMessageDTO;
import io.petstore.services.pet.PetServiceApi;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class PetTest {
    private Faker faker = new Faker();
    private PetServiceApi petServiceApi = new PetServiceApi();

    @Test
    @DisplayName("Проверка статуса 200 у POST + валидация JSON схемы + '/pet' + body with DTO json/application")
    @Tag("@method01")
    @Tag("@method01test01")
    public void postPet200() {
        PetDTO rqPetDTO = createNewPet(2L);
        ValidatableResponse rs = petServiceApi.postPet(rqPetDTO);
        rs.statusCode(200)
                // Добавил enum в json схему для проверки допустимых значений
                .body(matchesJsonSchemaInClasspath("schema/rs/PetDTOSchema.json"));
    }

    @Test
    @DisplayName("Проверка полей body запроса и ответа у POST + '/pet' + body with DTO json/application")
    @Tag("@method01")
    @Tag("@method01test02")
    public void postPetCompareRqBodyAndRsBody() {
        PetDTO rqPetDTO = createNewPet(2L);
        ValidatableResponse rs = petServiceApi.postPet(rqPetDTO);
        rs.statusCode(200);
        PetDTO rpPetDTO = rs.extract().body().as(PetDTO.class);
        assertTrue(rqPetDTO.equals(rpPetDTO), "rqPetDTO is not equal to rpPetDTO");
    }

    /*

     */
    @Test
    @DisplayName("Проверка запросов POST, PUT, GET, DELETE + '/pet' и их body")
    @Tag("@method01")
    @Tag("@method01test03")
    public void postGetDeletePetCompareRqBodyAndRsBody() {
        long id = 2L;
        // Step 1 - POST+GET - сравнение DTO объекта rqPostPetDTO с rpGetPetDTO
        PetDTO rqPostPetDTO = createNewPet(id);
        ValidatableResponse rsPost = petServiceApi.postPet(rqPostPetDTO);
        rsPost.statusCode(200);

        ValidatableResponse rsGet = petServiceApi.getPet(id);
        rsGet.statusCode(200);
        PetDTO rpGetPetDTO = rsGet.extract().body().as(PetDTO.class);
        comparePetDTO(rpGetPetDTO, rqPostPetDTO, "rpGetPetDTO01", "rqPostPetDTO");

        // Step 1 - PUT+GET - сравнение DTO объекта rqPutPetDTO с rpGetPetDTO + проверка того, что сгенерировался отличный объект от POST запроса
        PetDTO rqPutPetDTO = createNewPet(id);
        assertFalse(rqPutPetDTO.equals(rqPostPetDTO), "rqPostPetDTO is equal to rqPutPetDTO. Need different DTO");
        ValidatableResponse rsPut = petServiceApi.putPet(rqPutPetDTO);
        rsPut.statusCode(200);

        rsGet = petServiceApi.getPet(id);
        rsGet.statusCode(200);
        rpGetPetDTO = rsGet.extract().body().as(PetDTO.class);
        comparePetDTO(rpGetPetDTO, rqPutPetDTO, "rpGetPetDTO02", "rqPutPetDTO");

        // Step 3 - DELETE+GET - проверка удаления DTO
        ValidatableResponse rsDelete = petServiceApi.deletePet(id);
        rsDelete.statusCode(200)
                .body(matchesJsonSchemaInClasspath("schema/rs/RSCodeTypeMessageSchema.json"));
        RPCodeTypeMessageDTO rpDeleteBody = rsDelete.extract().body().as(RPCodeTypeMessageDTO.class);
        Assertions.assertAll("rsCodeTypeMessageDTO",
                () -> assertEquals(200L, rpDeleteBody.getCode()),
                () -> assertEquals("unknown", rpDeleteBody.getType()),
                () -> assertEquals(Long.toString(id), rpDeleteBody.getMessage())
        );

        rsGet = petServiceApi.getPet(id);
        rsGet.statusCode(404)
                .body(matchesJsonSchemaInClasspath("schema/rs/RSCodeTypeMessageSchema.json"));
        RPCodeTypeMessageDTO rpGetErrorBody = rsGet.extract().body().as(RPCodeTypeMessageDTO.class);
        Assertions.assertAll("rsCodeTypeMessageDTO",
                () -> assertEquals(1, rpGetErrorBody.getCode()),
                () -> assertEquals("error", rpGetErrorBody.getType()),
                () -> assertEquals("Pet not found", rpGetErrorBody.getMessage())
        );

    }

    private PetDTO createNewPet(Long id) {
        return PetDTO.builder()
                .id(id)
                .category(new Category(faker.random().nextLong(), faker.name().firstName()))
                .name(faker.name().firstName())
                .photoUrls(Arrays.asList("photoUrl1", "photoUrl2"))
                .tags(Arrays.asList(
                        new TagDTO(faker.random().nextLong(), faker.name().firstName()),
                        new TagDTO(faker.random().nextLong(), faker.name().firstName())
                ))
                .status("sold")
                .build();
    }

    private void comparePetDTO(PetDTO petDTO1, PetDTO petDTO2, String petDTO1Name, String petDTO2Name) {
        assertThat(petDTO1)
                .as(String.format("DTO '%s' is not equals DTO '%s'", petDTO1Name, petDTO2Name))
                .isEqualTo(petDTO2);
    }
}
