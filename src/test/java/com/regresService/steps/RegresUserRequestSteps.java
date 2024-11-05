package com.regresService.steps;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.complexAQA.utils.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegresUserRequestSteps {

    private static final String REGRES_HOST = Properties.getPropertyValue("regresHost");
    private static final String USER_URL = Properties.getPropertyValue("userURL");
    private static final RequestSpecification userRequestSpecification = RestAssured.given();

    /**
     * @param userID the ID of the user to retrieve
     * @return the HTTP response containing the user data
     */
    @Step("Sends a GET request to fetch a user by user ID={userID}")
    public Response getUserResponseById(int userID) {
        return userRequestSpecification
                .baseUri(REGRES_HOST + USER_URL + userID)
                .get();
    }

    /**
     * @param response the HTTP response to validate
     * @param userID   the expected user ID
     */
    @Step("Validate user ID in response matches expected ID={userID}")
    public void assertUserIdInResponse(Response response, int userID) {
        assertEquals(userID, response.jsonPath().getInt("data.id"), "Expected user ID does not match the response");
    }


    /**
     * @param response   the HTTP response to validate
     * @param statusCode the expected status code
     */
    @Step("Validate response status code matches expected code={statusCode}")
    public void assertResponseStatusCode(Response response, int statusCode) {
        response.then().statusCode(statusCode);
    }
}
