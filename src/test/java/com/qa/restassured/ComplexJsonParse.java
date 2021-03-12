package com.qa.restassured;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(payload.coursePrice());

        // Print No of courses returned by API
        System.out.println("Print No of courses returned by API");
        int courses = js.getInt("courses.size()");
        System.out.println(courses);
        // Print purchase Amount
        System.out.println("Print purchase Amount");
        int amount = js.getInt("dashboard.purchaseAmount");
        System.out.println(amount);
        //print title of the first course
        System.out.println("Print title of the first course");
        String firstCourse = js.getString("courses[0].title");
        System.out.println(firstCourse);
        //print all the course titles and respective prices
        System.out.println("Print all the course titles and respective prices");
        for (int i = 0; i < courses; i++) {
            String courseTitle = js.get("courses[" + i + "].title");
            System.out.println(js.get("courses[" + i + "].price").toString());
            System.out.println(courseTitle);

        }
        //Print no of copies sold by the RPA Course
        //index may change in array day by day
        //goal is to scan everything and if title is RPA then print no of copies sold
        System.out.println("Print no of copies sold by the RPA Course");
        for (int i = 0; i < courses; i++) {
            String courseRPA = js.get("courses[" + i + "].title");
            if (courseRPA.equalsIgnoreCase("RPA")) {
                //return the copies sold
                int copiesCount = js.getInt("courses[" + i + "].copies");
                System.out.println(copiesCount);
                //also chaecking Appium also so use break the loop if RPA is found
                break;
            }
        }
        // Verify if Sum of all Course prices matches with Purchase Amount
        System.out.println(" Verify if Sum of all Course prices matches with Purchase Amount");


    }
}
