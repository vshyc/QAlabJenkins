package pl.javastart.restassured.tests.pet;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.javastart.restassured.main.rop.DeletePetEndpoint;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

public class DeletePetTests extends SuiteTestBase {

    private static int nonExistingPetId;

    @BeforeAll
    public static void beforeTest(){
        nonExistingPetId = new Faker().number().numberBetween(1000, 10000);
        new DeletePetEndpoint().setPetId(nonExistingPetId).sendRequest();
    }

    @TmsLink("ID-2")
    @Severity(SeverityLevel.NORMAL)
    @Description("The goal of this test is to fail to delete non existing pet")
    @Test
    public void givenNonExistingPetWhenDeletingPetThenPetNotFoundTest() {
        new DeletePetEndpoint().setPetId(nonExistingPetId).sendRequest().assertStatusCode(HttpStatus.SC_NOT_FOUND);
    }

}
