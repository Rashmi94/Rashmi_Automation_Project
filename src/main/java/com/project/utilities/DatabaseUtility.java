package com.project.utilities;

import org.apache.logging.log4j.Logger;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseUtility {

    private static final String DB_URL = "";
    private static final String DB_USERNAME="";
    private static final String DB_PASSWORD="";
    private static Logger logger = LoggingManager.getLogging(DatabaseUtility.class);


    public Connection getConnection()
    {
        try {
            logger.info("starting DB connection");
            Connection connection = DriverManager.getConnection(DB_URL);
            logger.info("DB connection successful");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getDetails(String empId)
    {
        Map<String, String> empData = new HashMap<>();
        try {
            String query = "SELECT emp_firstname, emp_middle_name, emp_lastname FROM hs_hr_employee WHERE employee_id ="+empId;
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            empData.put("first name",result.getString("emp_firstname"));
            empData.put("last name",result.getString("emp_lastname"));
            empData.put("middle name",result.getString("emp_middle_name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empData;
    }
}
