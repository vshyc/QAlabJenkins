package pl.javastart.restassured.tests.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.apache.http.HttpStatus;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.javastart.restassured.main.rop.DeleteUserEndpoint;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

public class DeleteUserTests extends SuiteTestBase {

    private static String nonExistingUsername;

    @BeforeAll
    public static void beforeTest(){
        nonExistingUsername = new Faker().name().username();
        new DeleteUserEndpoint().setUsername(nonExistingUsername).sendRequest();
    }

    @TmsLink("ID-4")
    @Severity(SeverityLevel.NORMAL)
    @Description("The goal of this test is to fail to delete non-existing user")
    @Test
    public void givenNonExistingUserWhenDeletingUserThenUserNotFoundTest() {
        new DeleteUserEndpoint().setUsername(nonExistingUsername).sendRequest().assertStatusCode(HttpStatus.SC_NOT_FOUND);
    }

}
