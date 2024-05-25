package io.petstore.services.pet;

import io.petstore.services.BaseServiceApiAbstract;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class PetServiceApi extends BaseServiceApiAbstract {

    public PetServiceApi() {
        super();
        this.BASE_PATH = "/pet";
        spec(BASE_API_URL, BASE_PATH);
    }

    public ValidatableResponse getPet(int id) {
        return given()
                .basePath(this.BASE_PATH + "/" + id)
                .log().all()
                .when()
                .get()
                .then()
                .log().all();
    }

    public ValidatableResponse postPetViaForm(int id, String name, String status) {
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

    public ValidatableResponse deletePet(int id) {
        return given()
                .basePath(this.BASE_PATH + "/" + id)
                .log().all()
                .when()
                .delete()
                .then()
                .log().all();
    }
}