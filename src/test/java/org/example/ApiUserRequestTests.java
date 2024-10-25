package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.complexAQA.utils.Properties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test class for validating API responses for user requests
 */
public class ApiUserRequestTests {


    @Test
    @Description("Tests GET response status code")
    public void testStatusCode() {
        Response response = getUserResponseById(1);
        validateResponseStatusCode(response, 200);
    }


    @Test
    @Description("Test response body contain correct user ID")
    public void testUserIdInResponse() {
        Response response = getUserResponseById(1);
        validateUserIdInResponse(response, 1);
    }


    /**
     * @param userID the ID of the user to retrieve
     * @return the HTTP response containing the user data
     */
    @Step("Sends a GET request to fetch a user by user ID={userID}")
    public Response getUserResponseById(int userID) {
        RequestSpecification userGetRequest = RestAssured
                .given()
                .baseUri(Properties.getHost() + Properties.getUserUrl() + userID);

        return userGetRequest.get();
    }


    /**
     * @param response the HTTP response to validate
     * @param userID the expected user ID
     */
    @Step("Validate user ID in response matches expected ID={userID}")
    public void validateUserIdInResponse(Response response, int userID) {
        assertEquals(userID, response.jsonPath().getInt("data.id"));
    }


    /**
     * @param response the HTTP response to validate
     * @param statusCode the expected status code
     */
    @Step("Validate response status code matches expected code={statusCode}")
    public void validateResponseStatusCode(Response response, int statusCode) {
        response.then().statusCode(statusCode);
    }
}
