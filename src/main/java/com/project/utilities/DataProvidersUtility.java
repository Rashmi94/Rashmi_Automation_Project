package com.project.utilities;

import org.testng.annotations.DataProvider;

import java.util.List;

public class DataProvidersUtility {

    public static final String file_path = System.getProperty("user.dir")+"/src/test/resources/testdata/LoginData.xlsx";

    @DataProvider(name="validLoginCreds_DP")
    public Object[][] validLoginData()
    {
        return getSheetData("validLoginCreds");
    }

    @DataProvider(name="inValidLoginCreds_DP")
    public Object[][] inValidLoginData()
    {
        return getSheetData("invalidLoginCreds");
    }


    public Object[][] getSheetData(String sheetName)
    {
        List<String[]> sheetData = ExcelReaderUtility.getSheetData(file_path, sheetName);
        Object[][] data = new Object[sheetData.size()][sheetData.get(0).length];
        for(int i=0;i< sheetData.size();i++)
        {
            data[i]=sheetData.get(i);
        }
        return data;
    }
}
