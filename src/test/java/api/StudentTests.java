package api;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class StudentTests extends BaseTest{

    @Test
    public void createStudent() {

        String createBody = """
                {
                "firstName": "Mehmet",
                "lastName": "Simsek",
                "email": "test@example.com"
                }
                """;

        Response response = // Response nesnesine her zaman ihtiyac duymayiz.
                // Sadece nesneyi kullanacaksam bu sekilde tanimlariz

                // request islemleri
                given()
                        .spec(request) // base test icinde belirttigimiz request kosullari
                        .body(createBody) // request body post ve put isleminde gerekli

                        // http method (get, post, put, delete)
                        .when()
                        .post("/students")

                        // response islemleri
                        .then()

                        // bu kisim testler icin
                        .statusCode(201)
                        .body("id", notNullValue()) // response icerisinde id nin dolu oldugunun test
                        .body("firstName", equalTo("Mehmet")) // response icerisindeki firstname i check ettik
                        .extract().response(); // response nesnesi olusmasi icin bu satir var

        // response nesnesinden path() methodu ile response icerisindeki degerlere ulasabilirim.
        // key-value mantiginda
        System.out.println("Create edilen student id: " + response.path("id"));
    }
}
