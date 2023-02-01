package contact_ok;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class OkBase {
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    public final String baseUrl = "https://contactapp-telran-backend.herokuapp.com";

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoicXdlcnR5MzE3MUBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY3NTg1NjEwMCwiaWF0IjoxNjc1MjU2MTAwfQ.HaADwZjcN1uOvxLyJKzHfDbYelqSU5678bziZX8haWE";

}
