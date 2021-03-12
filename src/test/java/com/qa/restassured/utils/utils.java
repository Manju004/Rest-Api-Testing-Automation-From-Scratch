package com.qa.restassured.utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class utils {

    //static makes req not null for other test cases
    public static RequestSpecification req;

    public RequestSpecification requestSpecification() throws IOException {
        if (req == null) {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));

//logging filter is applied to the object - to log everything
            req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return req;
        }
        return req;
    }

    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("/Users/manjus/IdeaProjects/restAssuredDemo/src/test/java/com/qa/restassured/resources/global.properties");
        prop.load(fis);
        prop.get("baseUrl");
        return prop.getProperty(key);
    }

    public String getJsonPath(Response response, String key) {
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }

}
