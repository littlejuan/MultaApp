package org.littlejuan.multaapp.rest;

public class APIUtils {
    private APIUtils() {}

    public static final String BASE_URL = "http://192.168.2.14:8080/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
