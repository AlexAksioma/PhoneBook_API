package contact_rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import dto.ResponseMessageDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class RestLoginTests {

    @BeforeMethod
    public  void preCondition(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void loginPositiveTest(){
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("qwerty3171gmail.com")
                .password("Qwerty123!_")
                .build();

        AuthResponseDTO responseDTO = given()
                .body(requestDTO)
                .contentType("application/json")
                .when()
                .post("/user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(AuthResponseDTO.class)
                ;

        System.out.println(responseDTO.getToken());

    }

    @Test
    public void loginNegativeTest_WrongEmail(){
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("qwerty3171gmail.com")
                .password("Qwerty123!_")
                .build();

         ErrorDTO errorDTO = given()
                .body(requestDTO)
                .contentType("application/json")
                .when()
                .post("/user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .extract()
                .as(ErrorDTO.class)
                ;

        System.out.println("message --> "+errorDTO.getError().toString());

    }

    @Test
    public void loginNegativeTest_WrongPassword(){
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("qwerty3171@gmail.com")
                .password("Qwerty123")
                .build();

        ErrorDTO errorDTO =  given()
                .body(requestDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message", containsString("Login or Password"))
                .extract()
                .as(ErrorDTO.class)
                ;
        System.out.println(errorDTO.getMessage().toString()+"  status code --> "+errorDTO.getStatus());
    }
}
