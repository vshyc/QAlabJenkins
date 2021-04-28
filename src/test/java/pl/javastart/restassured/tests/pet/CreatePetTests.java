package pl.javastart.restassured.tests.pet;

import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.rop.CreatePetEndpoint;
import pl.javastart.restassured.main.rop.DeletePetEndpoint;
import pl.javastart.restassured.main.test.data.pet.PetTestDataGenerator;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

public class CreatePetTests extends SuiteTestBase {

    private Pet actualPet;

    @Issue("DEFECT-1")
    @TmsLink("ID-1")
    @Severity(SeverityLevel.CRITICAL)
    @Description("The goal of this test is to create pet and check if returned Pet object is the same")
    @Test
    public void givenPetWhenPostPetThenPetIsCreatedTest() {
        Pet pet = new PetTestDataGenerator().generatePet();

        actualPet = new CreatePetEndpoint().setPet(pet).sendRequest().assertRequestSuccess().getResponseModel();

        Assertions.assertThat(actualPet).describedAs("Send Pet was different than received by API").usingRecursiveComparison().isEqualTo(pet);
    }

    @AfterEach
    public void cleanUpAfterTest() {
        ApiResponse apiResponse = new DeletePetEndpoint().setPetId(actualPet.getId()).sendRequest().assertRequestSuccess().getResponseModel();
        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(HttpStatus.SC_OK);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(actualPet.getId().toString());

        Assertions.assertThat(apiResponse).describedAs("API Response from system was not as expected").usingRecursiveComparison().isEqualTo(expectedApiResponse);
    }

}
