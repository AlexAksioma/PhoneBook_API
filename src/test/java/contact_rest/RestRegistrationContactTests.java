package contact_rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;

public class RestRegistrationContactTests {

    @BeforeClass
    public  void preCondition(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void registrationPositiveTest(){
        int i = new Random().nextInt(1000)+1000;
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("qwertyAlex"+i+"@mail.de")
                .password(i+"Qwerty$")
                .build();
        AuthResponseDTO responseDTO = given()
                .body(requestDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("user/registration/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(AuthResponseDTO.class)
                ;
        System.out.println(responseDTO.getToken());
    }

    @Test
    public void registrationNegativeTest_WrongEmail(){
        int i = new Random().nextInt(1000)+1000;
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("qwertyAlex"+i+"mailde")
                .password(i+"Qwerty$")
                .build();
        ErrorDTO errorDTO = given()
                .body(requestDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("user/registration/usernamepassword")
                .then()
                .assertThat().statusCode(400)
                .extract()
                .as(ErrorDTO.class)
                ;
        System.out.println(errorDTO.getMessage().toString());
        Assert.assertTrue(errorDTO.getMessage().toString().contains("must be a well-formed email address"));
    }

    @Test
    public void registrationNegativeTest_Duplicate(){
        int i = new Random().nextInt(1000)+1000;
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("qwertyAlex"+i+"@mail.de")
                .password(i+"Qwerty$")
                .build();
        given()
                .body(requestDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("user/registration/usernamepassword")
                .then()
                ;

        ErrorDTO errorDTO = given()
                .body(requestDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("user/registration/usernamepassword")
                .then()
                .assertThat().statusCode(409)
                .extract()
                .as(ErrorDTO.class)
                ;
        System.out.println(errorDTO.getMessage().toString());
        Assert.assertTrue(errorDTO.getMessage().toString().contains("User already exists"));
    }
}
