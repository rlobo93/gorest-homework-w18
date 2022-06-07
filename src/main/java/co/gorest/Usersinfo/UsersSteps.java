package co.gorest.Usersinfo;


import co.gorest.constants.EndPoints;
import co.gorest.model.UsersPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;


public class UsersSteps {

    @Step("Creating user with userId: {0}, name: {1}, email: {2}, gender: {3}, status: {4}")
    public ValidatableResponse createUser(String name, String email, String gender, String status) {

        UsersPojo usersPojo = new UsersPojo();
        usersPojo.setName(name);
        usersPojo.setGender(email);
        usersPojo.setEmail(gender);
        usersPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(usersPojo)
                .when()
                .post()
                .then();
        
    }


    @Step("Getting the user information with name: {0}")
    public HashMap<String, Object> getUserInfoByName(String name) {

        String p1 = "findAll{it.name=='";
        String p2 = "'}.get(0)";

        HashMap<String, Object> userMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.USER)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + name + p2);
        return userMap;

    }


    @Step("Updating user information with userId: {0}, name: {1}, email: {2}, gender: {3}, status: {4}")
    public ValidatableResponse updateUser(int userId, String name, String gender, String email, String status) {

        UsersPojo usersPojo = new UsersPojo();
        usersPojo.setName(name);
        usersPojo.setGender(email);
        usersPojo.setEmail(gender);
        usersPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("userID", userId)
                .body(usersPojo)
                .when()
                .put(EndPoints.USER)
                .then();
    }


    @Step("Deleting user information with userId: {0}")
    public ValidatableResponse deleteUser(int userId) {

        return SerenityRest.given().log().all()
                .pathParam("UserID", userId)
                .when()
                .delete(EndPoints.USER)
                .then();
    }

    @Step("Getting user information with userId: {0}")
    public ValidatableResponse getUserById(int userId) {
        return SerenityRest.given().log().all()
                .pathParam("UserID", userId)
                .get(EndPoints.USER)
                .then();
    }


}
