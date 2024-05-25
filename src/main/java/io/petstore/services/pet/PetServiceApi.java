package io.petstore.services.pet;

import io.petstore.dto.pet.PetDTO;
import io.petstore.services.BaseServiceApiAbstract;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class PetServiceApi extends BaseServiceApiAbstract {

    public PetServiceApi() {
        super();
        this.BASE_PATH = "/pet";
        spec(BASE_API_URL, BASE_PATH);
    }

    public ValidatableResponse getPet(long id) {
        return given()
                .basePath(this.BASE_PATH + "/" + id)
                .log().all()
                .when()
                .get()
                .then()
                .log().all();
    }

    public ValidatableResponse postPet(PetDTO petDTO) {
        return given()
                .body(petDTO)
                .log().all()
                .when()
                .post()
                .then()
                .log().all();
    }

    public ValidatableResponse putPet(PetDTO petDTO) {
        return given()
                .body(petDTO)
                .log().all()
                .when()
                .put()
                .then()
                .log().all();
    }

    public ValidatableResponse postPetViaForm(long id, String name, String status) {
        return given()
                .basePath(this.BASE_PATH + "/" + id)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("name", name)
                .formParam("status", status)
                .header("Accept-Charset", "UTF-8")
                .header("Accept-Encoding", "gzip, deflate, br")
                .log().all()
                .when()
                .post()
                .then()
                .log().all();
    }

    public ValidatableResponse deletePet(long id) {
        return given()
                .basePath(this.BASE_PATH + "/" + id)
                .log().all()
                .when()
                .delete()
                .then()
                .log().all();
    }
}