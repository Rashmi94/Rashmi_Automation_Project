package com.project.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtility {

    //method to send get request
    public static Response getApiCall(String endpoint)
    {
        return RestAssured.get(endpoint);
    }

    //method to send post request
    public static void postApiCall(String endpoint, String payload)
    {
        RestAssured.given().baseUri(endpoint).body(payload).header("Content-Type","application/json")
                .when().post();
    }
    //method to validate the response
    public static boolean validateResponseStatusCode(Response response,int statusCode)
    {
        return response.getStatusCode()==statusCode;
    }

    //method to extract value from json response
    public static String extractResponse(Response response, String path)
    {
        return response.jsonPath().getString(path);
    }
}
