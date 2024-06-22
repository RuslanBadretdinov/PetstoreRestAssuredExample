package io.petstore.services;

import io.petstore.specs.BaseSpec;
import io.restassured.specification.RequestSpecification;

public abstract class BaseServiceApiAbstract extends BaseSpec{
    protected String BASE_API_URL;
    protected String BASE_PATH;
    private RequestSpecification requestSpec;

    protected BaseServiceApiAbstract() {
        this.BASE_API_URL = System.getProperty("base.api.url");
    }

    protected void spec(String baseApiURL, String basePath) {
        this.requestSpec = this.requestSpec(baseApiURL, basePath);
        this.installSpec(this.requestSpec, this.responseSpec());
    }
}