package com.qa.restassured;

import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {
    @Test
    public void sumofCourse() {
        // Verify if Sum of all Course prices matches with Purchase Amount
        System.out.println(" Verify if Sum of all Course prices matches with Purchase Amount");

        int sum = 0;
        JsonPath js = new JsonPath(payload.coursePrice());
        int count = js.getInt("courses.size()");
        for (int i = 0; i < count; i++) {
            int price = js.getInt("courses[" + i + "].price");
            int copies = js.getInt("courses[" + i + "].copies");
            int amount = price * copies;
            System.out.println(amount);
            sum = sum + amount;

        }
        System.out.println(sum);
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum, purchaseAmount);

        //testng and junit gives you the details about no.of test passes,failures,skips and test runs


    }
}

