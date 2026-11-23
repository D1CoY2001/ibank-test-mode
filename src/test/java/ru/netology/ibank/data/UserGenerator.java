package ru.netology.ibank.data;

import com.github.javafaker.Faker;

import static io.restassured.RestAssured.given;

public final class UserGenerator {

    private static final Faker faker = new Faker();

    private UserGenerator() {
    }

    public static RegistrationDto generateActiveUser() {
        return generateUser("active");
    }

    public static RegistrationDto generateBlockedUser() {
        return generateUser("blocked");
    }

    public static RegistrationDto generateUser(String status) {
        RegistrationDto user = new RegistrationDto(
                faker.name().username(),
                faker.internet().password(8, 12),
                status
        );

        given()
                .spec(AuthSpec.requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);

        return user;
    }
}