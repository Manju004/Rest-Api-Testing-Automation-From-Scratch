package com.qa.restassured.Steps;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {        //execute this code only when place id is null
        //write a code that will give you place id

        AddPlaceSteps m = new AddPlaceSteps();
        if (AddPlaceSteps.place_id == null) {

            m.addPlacePayload("Shetty", "French", "Asia");
            m.userCallsWithHttpRequest("AddPlaceAPI", "POST");
            m.verifyPlace_IdCreatedMapsToUsing("Shetty", "getPlaceAPI");
        }


    }
}
