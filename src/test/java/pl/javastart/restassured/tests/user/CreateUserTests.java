package pl.javastart.restassured.tests.user;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.user.User;
import pl.javastart.restassured.main.test.data.UserTestDataGenerator;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class CreateUserTests extends SuiteTestBase {

    private User user; // Tworzymy pole typu User, w celu przekazania go do metody cleanUpAfterTest()

    @Test
    public void givenUserWhenPostUserThenUserIsCreatedTest() {
        UserTestDataGenerator userTestDataGenerator = new UserTestDataGenerator();
        user = userTestDataGenerator.generateUser();

        ApiResponse apiResponse = given().contentType("application/json")
                .body(user)
                .when().post("user")
                .then().statusCode(200).extract().as(ApiResponse.class);

        assertEquals(apiResponse.getCode(), Integer.valueOf(200), "Code");
        assertEquals(apiResponse.getType(), "unknown", "Type");
        assertEquals(apiResponse.getMessage(), user.getId().toString(), "Message");
    }

    @AfterMethod
    public void cleanUpAfterTest(){
        ApiResponse apiResponse = given().contentType("application/json")
                .when().delete("user/{username}", user.getUsername()) // Usuwamy użytkownika z podanym username
                .then().statusCode(200).extract().as(ApiResponse.class);

        assertEquals(apiResponse.getCode(), Integer.valueOf(200), "Code");
        assertEquals(apiResponse.getType(), "unknown", "Type");
        assertEquals(apiResponse.getMessage(), user.getUsername(), "Message"); // Sprawdzamy, czy w odpowiedzi systemu znajduje się usunięte username użytkownika
    }

}
