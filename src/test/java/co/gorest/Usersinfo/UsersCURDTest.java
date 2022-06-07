package co.gorest.Usersinfo;


import co.gorest.constants.EndPoints;
import co.gorest.model.UsersPojo;
import co.gorest.testbase.TestBase;
import co.gorest.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;


import static org.hamcrest.Matchers.hasValue;



//@RunWith(SerenityRunner.class)
public class UsersCURDTest extends TestBase {

    static String name = "PrimUser" + TestUtils.getRandomValue();
    static String email = TestUtils.getRandomValue() + "xyz@gmail.com";
    static String gender = "PrimeUser" + TestUtils.getRandomValue();
    static String status = "active";
    static int userId;


    @Title("This will create a new user")
    @Test
    public void test001() {

        UsersPojo usersPojo = new UsersPojo();
        usersPojo.setName(name);
        usersPojo.setGender(email);
        usersPojo.setEmail(gender);
        usersPojo.setStatus(status);
        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(usersPojo)
                .when()
                .post(EndPoints.USER)
                .then().log().all().statusCode(201);

    }

    @Title("Verify if the user was added to the application")
    @Test
    public void test002() {
        String p1 = "findAll{it.name=='";
        String p2 = "'}.get(0)";

        HashMap<String, Object> userMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.USER)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + name + p2);
        Assert.assertThat(userMap, hasValue(name));
        userId = (int) userMap.get("id");
        System.out.println(userId);
    }


    @Title("Update the user information and verify the updated information")
    @Test
    public void test003() {

        name = name + "_updated";

        UsersPojo usersPojo = new UsersPojo();
        usersPojo.setName(name);
        usersPojo.setGender(email);
        usersPojo.setEmail(gender);
        usersPojo.setStatus(status);
        SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("userID", userId)
                .body(usersPojo)
                .when()
                .put(EndPoints.USER)
                .then().log().all().statusCode(200);

        String p1 = "findAll{it.name=='";
        String p2 = "'}.get(0)";

        HashMap<String, Object> userMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.USER)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + name + p2);
        Assert.assertThat(userMap, hasValue(name));

    }


    @Title("Delete the user and verify if the user is deleted")
    @Test
    public void test004(){
        SerenityRest.given().log().all()
                .pathParam("userID", userId)
                .when()
                .delete(EndPoints.USER)
                .then().statusCode(204);

        SerenityRest.given().log().all()
                .pathParam("userID", userId)
                .get(EndPoints.USER)
                .then()
                .statusCode(404);

    }



}
