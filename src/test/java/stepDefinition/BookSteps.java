package stepDefinition;

import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import utility.ConfigReader;

public class BookSteps {

    public ConfigReader configReader;

    @Then("I get the list of books")
    public void i_get_the_list_of_books(){
        configReader = new ConfigReader();
        String BASE_URL = configReader.getProperty("base.url");
        Response response = RestAssured
                .given()
                .baseUri(BASE_URL)
                .basePath("/BookStore/v1/Books")
                .log().all()  // Log request details
                .when()
                .get()
                .then()
                .log().all()  // Log response details
                .extract().response();

        System.out.println("Response Body: " + response.getBody().asString());


        response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .time(lessThan(3000L))
                .body("books.size()", equalTo(8))
                .body("books[0].isbn", notNullValue())
                .body("books.title", hasItem("Git Pocket Guide"));
    }
}
