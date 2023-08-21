package com.niveus;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class App {

    private static final String DB_URL = "jdbc:postgresql://130.211.199.173:5432/gcp-sql-db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "121212";

    public static void main(String[] args) {
        // Create a connection to the database
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            // Insert a new record (POST)
        	// Usage example with String values for empId, name, salary, and a java.sql.Date object for dob
   	insertRecord(connection, 13 , "Vineeth", 900 , Date.valueOf("1988-08-12"));
        	


            // Retrieve a record by ID (GET)
    	retrieveAllRecords(connection);


            // Update a record by ID (PUT)
         //Usage example to update record with EmpID 1
     updateRecordById(connection, 1, "kiran", 400, Date.valueOf("2000-01-01"));


            // Delete a record by ID (DELETE)
          deleteRecordById(connection, 1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertRecord(Connection connection, Integer empId, String name, Integer salary, Date dob) throws SQLException {
      

    	String insertSQL = "INSERT INTO \"Employee\" (\"EmpID\", \"Name\", \"Salary\", \"DOB\") VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
        	preparedStatement.setInt(1, empId);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, salary);
            preparedStatement.setDate(4, dob);
            preparedStatement.executeUpdate();
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Record inserted successfully.");
            } else {
                System.out.println("No rows were inserted.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception here or log it
        }
    }


    // Usage example with String values for empId and salary



    private static void retrieveAllRecords(Connection connection) throws SQLException {
        String selectSQL = "SELECT \"EmpID\", \"Name\", \"Salary\", \"DOB\" FROM \"Employee\"";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Process and print each retrieved record
                    System.out.println("EmpID: " + resultSet.getInt("EmpID"));
                    System.out.println("Name: " + resultSet.getString("Name"));
                    System.out.println("Salary: " + resultSet.getDouble("Salary"));
                    System.out.println("DOB: " + resultSet.getDate("DOB"));
                    System.out.println("--------------------");
                }
            }
        }
    }


    private static void updateRecordById(Connection connection, Integer empId, String name, Integer salary, Date dob) throws SQLException {
    	String updateSQL = "UPDATE \"Employee\" SET \"Name\" = ?, \"Salary\" = ?, \"DOB\" = ? WHERE \"EmpID\" = ?";
    	try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
    	    preparedStatement.setString(1, name);
    	    preparedStatement.setInt(2, salary);
    	    preparedStatement.setDate(3, dob);
    	    preparedStatement.setInt(4, empId);
    	    preparedStatement.executeUpdate();
    	    
   int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Record inserted successfully.");
            } else {
                System.out.println("No rows were inserted.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception here or log it
        }
    }
   
    private static void deleteRecordById(Connection connection, int empId) throws SQLException {
        String deleteSQL = "DELETE FROM \"Employee\"  WHERE \"EmpID\" = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, empId);
            preparedStatement.executeUpdate();
        }
    }

}
