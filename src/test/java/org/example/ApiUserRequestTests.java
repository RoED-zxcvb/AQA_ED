package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.complexAQA.utils.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * TestClass for GET response
 * Libraries: API call with RestAssured
 */
public class ApiUserRequestTests {

    private Response response;

    @BeforeEach
    public void getResponse() {
        RequestSpecification userGetRequest = RestAssured
                .given()
                .baseUri(Properties.getHost() + Properties.getUserUrl() + 1);

        response = userGetRequest.get();
    }


    /**
     * Test GET response
     * Libraries: RestAssured assert
     */
    @Test
    public void statusCodeTestWithRestAssured() {
        response.then().statusCode(200);
    }


    /**
     * Test response body contain correct user ID
     * Libraries: Junit assert
     */
    @Test
    public void responseAttributeTestWithRestAssuredAndJunitAssert() {
        assertEquals(1, response.jsonPath().getInt("data.id"));
    }
}
