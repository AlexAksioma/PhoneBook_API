package contact_ok;

//fa779c5b-da28-40de-a134-4cfa54c670f6

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ResponseMessageDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/*
    fa779c5b-da28-40de-a134-4cfa54c670f6
    ptohg1370@mail.com
    39713663183
    */
public class OkEditContactTests extends  OkBase{
    ContactDTO contact = ContactDTO.builder()
            .id("fa779c5b-da28-40de-a134-4cfa54c670f6")
            .name("EditName")
            .lastName("EditLast")
            .email("qw@qq.ed")
            .phone("012345678911")
            .address("rgrtgredit")
            .description("gbgfbrbhgbEdit")
            .build();

    @Test
    public void editContactById() throws IOException {
        RequestBody  requestBody = RequestBody.create(gson.toJson(contact), JSON);
        Request request = new  Request.Builder()
            .url(baseUrl+"/v1/contacts")
            .addHeader("Authorization",token)
            .put(requestBody)
            .build();
        Response response= client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        ResponseMessageDTO messageDTO = gson.fromJson(response.body().string(),ResponseMessageDTO.class);
        System.out.println("--> "+messageDTO.getMessage());
    }
}
