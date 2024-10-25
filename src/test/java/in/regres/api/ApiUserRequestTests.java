package in.regres.api;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.complexAQA.utils.Properties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test class for validating API responses for user requests
 */
@Epic("User API")
@Story("Retrieve user information")
@Feature("User Retrieval")
public class ApiUserRequestTests {

    private static final String REGRES_HOST = Properties.getPropertyValue("regresHost");
    private static final String USER_URL = Properties.getPropertyValue("userURL");
    private static final RequestSpecification userRequestSpecification = RestAssured.given();


    @Description("Verifies that the response status code is 200 for a valid user request")
    @Test
    public void testResponseStatusCodeForUserGetApi200() {
        Response response = getUserResponseById(1);
        validateResponseStatusCode(response, 200);
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5})
    @Description("Verification of correct User ID in response for multiple users")
    public void testUserIdInResponseForUserGetApi(int userID) {
        Response response = getUserResponseById(userID);
        validateUserIdInResponse(response, userID);
    }


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
    public void validateUserIdInResponse(Response response, int userID) {
        assertEquals(userID, response.jsonPath().getInt("data.id"), "Expected user ID does not match the response");
    }


    /**
     * @param response   the HTTP response to validate
     * @param statusCode the expected status code
     */
    @Step("Validate response status code matches expected code={statusCode}")
    public void validateResponseStatusCode(Response response, int statusCode) {
        response.then().statusCode(statusCode);
    }
}
