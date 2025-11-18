package api;

import api.pojo.Student;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class StudentTestsWithPojo extends BaseTest{

    private int studentID;

    @Test(priority = 1, description = "Create a new student")
    public void createStudent() {

        Student requestStudent =
                new Student("Baris", "Cansiz", "baris@example.com");

        Student createdStudent =
                given()
                        .spec(request)
                        .body(requestStudent) // POJO -> JSON

                        .when()
                        .post("/students")

                        .then()
                        .statusCode(201)
                        .body("id", notNullValue())
                        .body("firstName", equalTo("Baris"))
                        .extract()
                        .as(Student.class); // as() metodu ile hangi tipte oldugunu belirtiyoruz.
        // Pojo kullanimi burda bize tip güvenligini saglar.

        System.out.println("Created student: " + createdStudent);
        studentID = createdStudent.getId(); // path response a ait, burada yeni student
        // create ettigimiz icin direkt getId ile id sine ulasilabiliyor
    }
}
