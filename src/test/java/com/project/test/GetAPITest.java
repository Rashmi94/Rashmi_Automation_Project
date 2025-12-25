package com.project.test;

import com.google.protobuf.Api;
import com.project.utilities.ApiUtility;
import com.project.utilities.ExtentManager;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetAPITest {

    @Test
    public void verifyGetUserAPI()
    {
        ExtentManager.logStep("Starting endpoint : "+"https://jsonplaceholder.typicode.com/users/1");
        Response response = ApiUtility.getApiCall("https://jsonplaceholder.typicode.com/users/1");
        ExtentManager.logStep("Adding validations");
        if(ApiUtility.validateResponseStatusCode(response,200))
            ExtentManager.logPassAPI("Api passed successfully");
        else
            ExtentManager.logFailureAPI("Api failed");

        ExtentManager.logStep("asserting name, username, city");
        String name = ApiUtility.extractResponse(response,"name");
        String userName = ApiUtility.extractResponse(response,"username");
        String city = ApiUtility.extractResponse(response,"address.city");

        SoftAssert sa = new SoftAssert();
        sa.assertEquals(name, "Leanne Graham");
        sa.assertEquals(userName,"Bret");
        sa.assertEquals(city,"Gwenborough");
        sa.assertAll();

    }

}
