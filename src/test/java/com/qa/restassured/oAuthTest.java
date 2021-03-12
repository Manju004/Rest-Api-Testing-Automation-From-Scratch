package com.qa.restassured;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import pojo.GetCourse;
import pojo.api;

import static io.restassured.RestAssured.*;

public class oAuthTest {
    public static void main(String[] args) {

//        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("manjusrinivasan04@gmail.com");
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//        Thread.sleep(2500);
//        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("xxxxx");
//        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//        Thread.sleep(4000);
//       String url = driver.getCurrentUrl();
//
        String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AY0e-g7H14yyKF9n4dLiOasjizRcRv4A3OAEtRDaPivNuw8vVjXON0M-qBU3WgV2EKILhQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent#";
        String partialCode = url.split("code=")[1];
        String code = partialCode.split("&scope")[0];
        System.out.println(code);


        String accessTokenResponse = given().urlEncodingEnabled(false).queryParams("code", code)
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
                .queryParams("state", "verifyfjdss")
                .queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
                .when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");
        // System.out.println(accessToken);


        GetCourse getCourseresponse = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);


        //trying to get linkedin and instructor property from the JSON
        System.out.println(getCourseresponse.getLinkedin());
        System.out.println(getCourseresponse.getInstructor());
        System.out.println(getCourseresponse.getCourses().getApi().get(1).getCourseTitle());


        List<api> apiCourses = getCourseresponse.getCourses().getApi();
        for (int i = 0; i < apiCourses.size(); i++) {
            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println(apiCourses.get(i).getPrice());
            }
        }

        //Get the course names of WebAutomation
        //if array is dynamic in nature then go for arraylist because in runtime we can increase size
        ArrayList<String> a = new ArrayList<>();

        List<pojo.webAutomation> w = getCourseresponse.getCourses().getWebAutomation();

        for (int j = 0; j < w.size(); j++) {
            a.add(w.get(j).getCourseTitle());
        }

        //convert array to arraylist
        List<String> expectedList = Arrays.asList(courseTitles);

        Assert.assertTrue(a.equals(expectedList));


    }
}
