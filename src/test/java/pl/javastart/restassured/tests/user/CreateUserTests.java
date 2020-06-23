package pl.javastart.restassured.tests.user;

import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.user.User;
import pl.javastart.restassured.main.test.data.UserTestDataGenerator;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class CreateUserTests extends SuiteTestBase {

    @Test
    public void givenUserWhenPostUserThenUserIsCreatedTest() {
        UserTestDataGenerator userTestDataGenerator = new UserTestDataGenerator(); // Tworzymy generator
        User user = userTestDataGenerator.generateUser(); // Generujemy obiekt User

        ApiResponse apiResponse = given().contentType("application/json")
                .body(user)
                .when().post("user")
                .then().statusCode(200).extract().as(ApiResponse.class);

        assertEquals(apiResponse.getCode(), Integer.valueOf(200), "Code");
        assertEquals(apiResponse.getType(), "unknown", "Type");
        assertEquals(apiResponse.getMessage(), user.getId().toString(), "Message");
    }

}
