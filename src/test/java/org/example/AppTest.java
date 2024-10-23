package org.example;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.complexAQA.utils.Properties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Simple API test
 * Here testing GET response get code 200 and contain correct user ID
 * Libraries: RestAssured, Junit
 */
public class AppTest {

    @Test
    public void someTest() {

        RequestSpecification getRequest =
                RestAssured
                        .given()
                        .baseUri(Properties.getHost() + Properties.getUserUrl() + 1);

        Response response = getRequest
                .get();

        response.then()
                .statusCode(200);

        assertEquals(1, response.jsonPath().getInt("data.id"));
    }
}
