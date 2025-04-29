package com.mycompany;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import spark.Spark;

public class SurveyAPI {
    public static void main(String[] args) {
        
        // Set the port to 8080
        Spark.port(8080);

        // Allow cross-origin requests (CORS setup)
        Spark.before((req, res) -> {
            // Set CORS headers for all incoming requests
            res.header("Access-Control-Allow-Origin", "*"); // Allow all origins (use specific URL for production)
            res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); // Allow HTTP methods
            res.header("Access-Control-Allow-Headers", "Content-Type, Authorization"); // Allow headers
        });

        // Handle OPTIONS requests to respond to pre-flight checks
        Spark.options("/*", (request, response) -> {
            // Responding to pre-flight OPTIONS requests
            response.status(200);
            return "OK";
        });

        // Define the route to handle GET requests for /responses
        Spark.get("/responses", (req, res) -> {
            res.type("application/json");
        
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT q.question_text, r.rating FROM responses r JOIN questions q ON r.question_id = q.question_id")) {
        
                JSONArray result = new JSONArray();
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    String question = rs.getString("question_text");
                    String rating = rs.getString("rating");  // Fetching rating as string
                    System.out.println("Question: " + question + ", Rating: " + rating);  // Debugging line
                    obj.put("question", question);
                    obj.put("rating", rating);
                    result.put(obj);
                }
        
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return "{\"error\": \"Internal Server Error: " + e.getMessage() + "\"}";
            }
        });
        
        
    }
}
