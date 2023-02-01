package contact_ok;

import com.google.gson.Gson;
import dto.ErrorDTO;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpLoginTests extends OkBase{



    @Test
    public void loginTest() throws IOException {
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("qwerty3171gmail.com")
                .password("Qwerty123!_")
                .build();


        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url(baseUrl+"/v1/user/login/usernamepassword")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String responseJson = response.body().string();
            AuthResponseDTO responseDTO = gson.fromJson(responseJson, AuthResponseDTO.class);
            System.out.println(responseDTO.getToken());
            System.out.println(response.code());
            System.out.println("+++++++++++++++++++++++++++");
            Assert.assertTrue(response.isSuccessful());
        } else {
            System.out.println("Response code = " + response.code());
            ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
            System.out.println(errorDTO.getStatus()
                    + "-->" + errorDTO.getMessage()
                    + "--> " + errorDTO.getError());
            Assert.assertFalse(response.isSuccessful());
        }

    }
}
