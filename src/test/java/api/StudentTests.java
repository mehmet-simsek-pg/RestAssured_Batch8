package api;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class StudentTests extends BaseTest{

    private int studentID; // bu degiskeni ancak tüm testleri
    // ayni anda calistirirsak kullanbiliriz.

    @Test(priority = 1, description = "Create a new student")
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
        studentID = response.path("id");
    }

    @Test(priority = 2, description = "Get all students infos")
    public void getAllStudents() {

        Response response = // response body e ihtiyacim oldugu icin nesne ürettik
        given()
                .spec(request)

                .when()
                .get("/students")

                .then()
                .statusCode(200)
                .extract().response();

        // Response body i ekrana yazdirdik
        response.body().prettyPrint();
    }

    @Test(priority = 3, description = "Get student data by id")
    public void getStudentById() {

        Response response =
                given()
                        .spec(request)

                        .when()
                        .get("/students/{id}", studentID)

                        .then()
                        .statusCode(200)
                        .extract().response();

        response.body().prettyPrint();
    }

    @Test(priority = 4, description = "Update student data")
    public void updateStudent() {

        String updateBody = """
                {
                  "firstName": "Ali",
                  "lastName": "Güncellendi",
                  "email": "ali.updated@example.com"
                }
                """;

        given()
                .spec(request)
                .body(updateBody)

                .when()
                .put("/students/{id}", studentID)

                .then()
                .statusCode(200);
    }

    @Test(priority = 5, description = "Delete existing student")
    public void deleteStudent() {

        given()
                .spec(request)

                .when()
                .delete("/students/{id}", studentID)

                .then()
                .statusCode(204);

    }

}
