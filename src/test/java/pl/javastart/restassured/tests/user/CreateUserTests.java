package pl.javastart.restassured.tests.user;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.user.User;
import pl.javastart.restassured.main.rop.CreateUserEndpoint;
import pl.javastart.restassured.main.rop.DeleteUserEndpoint;
import pl.javastart.restassured.main.test.data.UserTestDataGenerator;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

public class CreateUserTests extends SuiteTestBase {

    private static User user;

    @TmsLink("ID-3")
    @Severity(SeverityLevel.CRITICAL)
    @Description("The goal of this test is to create new user")
    @Test
    public void givenUserWhenPostUserThenUserIsCreatedTest() {
        UserTestDataGenerator userTestDataGenerator = new UserTestDataGenerator();
        user = userTestDataGenerator.generateUser();

        ApiResponse apiResponse = new CreateUserEndpoint().setUser(user).sendRequest().assertRequestSuccess().getResponseModel();

        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(HttpStatus.SC_OK);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(user.getId().toString());

        Assertions.assertThat(apiResponse).describedAs("Created User was not created by API").usingRecursiveComparison().isEqualTo(expectedApiResponse);
    }

    @AfterAll
    public static void cleanUpAfterTest() {
        ApiResponse apiResponse = new DeleteUserEndpoint().setUsername(user.getUsername()).sendRequest().assertRequestSuccess().getResponseModel();

        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(HttpStatus.SC_OK);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(user.getUsername());

        Assertions.assertThat(apiResponse).describedAs("User was not deleted").usingRecursiveComparison().isEqualTo(expectedApiResponse);
    }

}
