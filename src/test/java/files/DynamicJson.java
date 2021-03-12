package files;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all().header("Content-Type", "application/json")
                .body(payload.AddBook(isbn, aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        //next time when we run this,test will fail because book already exists
        JsonPath js = ReusableMethods.rawToJson(response);
        String id = js.get("ID");
        System.out.println(id);
    }

    //parameterize API tests with multiple data sets
    @DataProvider(name = "BooksData")
    public Object[][] getData() {
        //array = collection of elements
        //multidimensional array = collection of arrays
        //each array will be given to the addBook method, each element will be executed and each array will be executed
        //creating object and initialising it
        return new Object[][]{{"mybook", "12344"}, {"newbook", "3456"}, {"book", "896745"}};

    }

}
