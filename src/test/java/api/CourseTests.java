package api;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourseTests extends BaseTest{

    private int courseID;

    @Test(priority = 1, description = "Create a new course")
    public void createCourse() {
        String createBody = """
                {
                  "code": "API101",
                  "title": "API Testing Giriş",
                  "description": "HTTP, status code ve JSON body testleri",
                  "credit": 4
                }
                """;

        Response response =
                given()
                        .spec(request)
                        .body(createBody)

                        .when()
                        .post("/courses")

                        .then()
                        .statusCode(201)
                        .body("id", notNullValue())
                        .body("credit", equalTo(4))
                        .extract().response();

        courseID = response.path("id");
    }

    @Test(priority = 2, description = "Get all course data")
    public void getAllCourses() {

        Response response =
        given()
                .spec(request)

                .when()
                .get("/courses")

                .then()
                .statusCode(200)
                .extract().response();

        response.body().prettyPrint(); // Ekrana yazdirmak icin
    }

    @Test(priority = 3, description = "Get course by id")
    public void getCourseById() {

        given()
                .spec(request)

                .when()
                .get("/courses/{id}", courseID)

                .then()
                .statusCode(200);
    }

    @Test(priority = 4, description = "Update course")
    public void updateCourse() {
        String updateBody = """
                {
                  "code": "API201",
                  "title": "API Testing İleri",
                  "description": "Auth, token ve hata yönetimi",
                  "credit": 5
                }
                """;

        given()
                .spec(request)
                .body(updateBody)

                .when()
                .put("/courses/{id}", courseID)

                .then()
                .statusCode(200);
    }

    @Test(priority = 5, description = "Delete course")
    public void deleteCourse() {

        given()
                .spec(request)

                .when()
                .delete("/courses/{id}", courseID)

                .then()
                .statusCode(204);
    }
}
