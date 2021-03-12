package com.qa.restassured.Steps;

import com.qa.restassured.resources.APIResources;
import com.qa.restassured.resources.TestdataBuild;
import com.qa.restassured.utils.utils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import static org.junit.Assert.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AddPlaceSteps extends utils {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    static String place_id;
    TestdataBuild data = new TestdataBuild();

    @Given("Add Place payload {string} {string} {string}")
    public void addPlacePayload(String name, String language, String address) throws IOException {

        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        res = given().spec(requestSpecification()).body(data.addPlace(name, language, address));
    }

    @When("user calls {string} with {string} http request")
    public void userCallsWithHttpRequest(String resource, String method) {
        //constructor will be called with value of resource which you pass
        APIResources resorceAPI = APIResources.valueOf(resource);
        System.out.println(resorceAPI.getResource());
        if (method.equalsIgnoreCase("POST")) {
            response = res.when().post(resorceAPI.getResource());
            //then().spec(resspec).extract().response();
        } else if (method.equalsIgnoreCase("GET"))
            response = res.when().get(resorceAPI.getResource());
    }

    @Then("the API call got success with status code {int}")
    public void theAPICallGotSuccessWithStatusCode(int arg0) {
        assertEquals(response.getStatusCode(), 200);
    }

    @Then("{string} in response body is {string}")
    public void inResponseBodyIs(String key, String value) {

        assertEquals(getJsonPath(response, key), value);
    }

    @And("verify place_Id created maps to {string} using {string}")
    public void verifyPlace_IdCreatedMapsToUsing(String name, String method) throws IOException {
        place_id = getJsonPath(response, "place_id");
        res = given().spec(requestSpecification()).queryParam("place_id", place_id);
        userCallsWithHttpRequest(method, "GET");
        String actualName = getJsonPath(response, "name");
        assertEquals(actualName, name);

    }

    @Given("DeletePlace payload")
    public void deleteplacePayload() throws IOException {
        res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
    }


}
