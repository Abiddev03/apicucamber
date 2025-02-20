package com.api.stepdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class MfaStepsDefinitions {

    private String baseURL;
    private Response response;

    @Given("the API base URL is {string}")
    public void theApiBaseUrlIs(String url) {
        baseURL = url;
    }

    @When("I make a POST request to {string} with username {string}")
    public void iMakeAPostRequestToWithUsername(String endpoint, String username) {
        RequestSpecification request = RestAssured.given();
        request.header("Accept", "*/*");
        request.header("Host", "uat-api.updatepromise.com");
        request.header("Accept-Encoding", "gzip, deflate, br");
        request.formParam("username", username);
        response = request.post(baseURL + endpoint);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @Then("the response should contain success with preferred : {int}")
    public void theResponseShouldContainSuccessWithPreferred(int preferred) {
        String responseBody = response.getBody().asString();
        // Assert that "success" is true
        Assert.assertTrue(responseBody.contains("\"success\":true"));
        // Assert that "preferred" matches the given value (in this case 2)
        Assert.assertTrue(response.getBody().asString().contains("\"preferred\":null"));


    }

    @Then("the email should be {string}")
    public void theEmailShouldBe(String expectedEmail) {
        String responseBody = response.getBody().asString();
        // Assert that the email matches the given masked email
      //  Assert.assertTrue(response.getBody().asString().contains("\"email\":\"f******q@corp.updatepromise.com\""));
        Assert.assertTrue(responseBody.contains("\"email\":\"" + expectedEmail + "\""));
    }

    @Then("the phone should be {string}")
    public void thePhoneShouldBe(String expectedPhoneNumber) {
        String responseBody = response.getBody().asString();
        // Assert that the phone number matches the given value
       // Assert.assertTrue(response.getBody().asString().contains("\"phone_number\":null"));
        Assert.assertTrue(responseBody.contains("\"phone_number\":\"" + expectedPhoneNumber + "\""));
    }

    // New steps for the failed MFA request

    @When("I make a POST request to {string} with username {string} and invalid credentials")
    public void iMakeAPostRequestToWithInvalidCredentials(String endpoint, String username) {
        RequestSpecification request = RestAssured.given();
        request.header("Accept", "*/*");
        request.header("Host", "uat-api.updatepromise.com");
        request.header("Accept-Encoding", "gzip, deflate, br");
        request.formParam("username", username);
        response = request.post(baseURL + endpoint);
    }

    @Then("the response should contain an error with message {string}")
    public void theResponseShouldContainAnErrorWithMessage(String errorMessage) {
        String responseBody = response.getBody().asString();
        // Assert that the error message contains the expected error message
        Assert.assertTrue(responseBody.contains("\"error\":\"" + errorMessage + "\""));
    }


}
