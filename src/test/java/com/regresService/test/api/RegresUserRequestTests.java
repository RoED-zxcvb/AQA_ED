package com.regresService.test.api;

import com.regresService.steps.RegresUserRequestSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


/**
 * Test class for validating API responses for user requests
 */
@Epic("User API")
@Story("Retrieve user information")
@Feature("User Retrieval")
public class RegresUserRequestTests {

    private final RegresUserRequestSteps regresUserRequestSteps = new RegresUserRequestSteps();


    @Description("Verifies that the response status code is 200 for a valid user request")
    @Test
    public void testResponseStatusCodeForUserGetApi200() {
        Response response = regresUserRequestSteps.getUserResponseById(1);
        regresUserRequestSteps.assertResponseStatusCode(response, 200);
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5})
    @Description("Verification of correct User ID in response for multiple users")
    public void testUserIdInResponseForUserGetApi(int userID) {
        Response response = regresUserRequestSteps.getUserResponseById(userID);
        regresUserRequestSteps.assertUserIdInResponse(response, userID);
    }


}
