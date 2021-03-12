package com.qa.restassured;

import files.ReusableMethods;
import files.payload;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class basic {

    public static void main(String[] args) throws IOException {

//Validate if add place API is working fine
        //given - all input details
        //When - submit the api - resource,http method
        //Then - validate the response
        //json file is static it wont change for using that file - convert the content of the file to the string -> content of file into Byte -> Byte data to the string

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        //Store the extracted response in the string
        String response = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get("/Users/manjus/Downloads/AddPlace.json")))).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", equalTo("Apache/2.4.18 (Ubuntu)"))
                .extract().response().asString();

        //header before then is input but header after then is for validations
        //sever - checking whether data is fetched from correct

        System.out.println(response);

        //Now add place -> Update place with new address -> Get place to validate if new address is present in the response

        JsonPath js = new JsonPath(response); //for parsing Json
        String placeId = js.getString("place_id");
        System.out.println(placeId);

        //update pace with new address
        String newAddress = "Summer walk, Africa";
        given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n").when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

        //Get place - everything is url - no body - place id is needed

        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200)
                .extract().response().asString();

        JsonPath jsget = ReusableMethods.rawToJson(getPlaceResponse);//it will return one object
        //JsonPath jsget = new JsonPath(getPlaceResponse);
        String actualaddress = jsget.getString("address");
        System.out.println(actualaddress);

        //testng
        Assert.assertEquals(actualaddress, newAddress);


    }
}
