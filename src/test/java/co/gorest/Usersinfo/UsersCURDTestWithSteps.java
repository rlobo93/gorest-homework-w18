package co.gorest.Usersinfo;


import co.gorest.testbase.TestBase;
import co.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;


import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class UsersCURDTestWithSteps extends TestBase {

    static String name = "PrimUser" + TestUtils.getRandomValue();
    static String email = TestUtils.getRandomValue() + "xyz@gmail.com";
    static String gender = "PrimeUser" + TestUtils.getRandomValue();
    static String status = "active";
    static int userId;


    @Steps
    UsersSteps usersSteps;


    @Title("This will create a new user")
    @Test
    public void test001() {

        ValidatableResponse response = usersSteps.createUser(name, email, gender, status);
        response.log().all().statusCode(201);

    }

    @Title("Verify if the user was added to the application")
    @Test
    public void test002() {

        HashMap<String, Object> userMap = usersSteps.getUserInfoByName(name);

        Assert.assertThat(userMap, hasValue(name));
        userId = (int) userMap.get("id");
        System.out.println(userId);

    }


    @Title("Update the user information and verify the updated information")
    @Test
    public void test003() {

        name = name + "_updated";

        usersSteps.updateUser(userId, name, email, gender, status).log().all().statusCode(200);

        HashMap<String, Object> studentMap = usersSteps.getUserInfoByName(name);
        Assert.assertThat(studentMap, hasValue(name));

    }


    @Title("Delete the user and verify if the user is deleted")
    @Test
    public void test004() {

        usersSteps.deleteUser(userId).log().all().statusCode(204);

        usersSteps.getUserById(userId).statusCode(404);


    }

}
