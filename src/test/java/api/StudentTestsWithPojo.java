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

    @Test(priority = 2, description = "Get all students info")
    public void getAllStudents() {

        Student[] students =
                given()
                        .spec(request)

                        .when()
                        .get("/students")

                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Student[].class);


        System.out.println("Toplam ögrenci sayisi: " + students.length);
        for(Student student: students) {
            System.out.println(student);
        }
    }

    @Test(priority = 3, description = "Get student by id")
    public void getStudentById() {

        Student student =
                given()
                        .spec(request)

                        .when()
                        .get("/students/{id}", studentID)

                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Student.class);

        System.out.println(student);
    }

    @Test(priority = 4, description = "Update student")
    public void updateStudent() {

        Student updateRequest =
                new Student("Bariss", "Updated", "updated@example.com");

        Student updatedStudent =
                given()
                        .spec(request)
                        .body(updateRequest)

                        .when()
                        .put("/students/{id}",studentID)

                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Student.class);

        System.out.println(updatedStudent);
    }

    @Test(priority = 5, description = "Delete existing student")
    public void deleteStudent() {
        /**
         * Eger response u bir nesneye atayacaksam response ya da buradaki gibi student
         * extract() ve as() metodlarini kullaniyoruz. Yoksa islemlere direkt olarak given() metoduyla
         * baslariz. Yani Student ve ya Response a esitlemek zorunlulugu yok hic bir testte.
         * */
        given()
                .spec(request)

                .when()
                .delete("/students/{id}", studentID)

                .then()
                .statusCode(204);
    }

}
