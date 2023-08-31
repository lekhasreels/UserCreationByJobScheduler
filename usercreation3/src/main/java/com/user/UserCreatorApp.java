package com.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class UserCreatorApp {
	public static void main(String[] args) {
        // Database connection parameters
        final String dbUrl = "jdbc:mysql://localhost:3306/userdb";
        final String dbUser = "root";
        final String dbPassword = "Sree27@532";

        // Create a timer
        Timer timer = new Timer();

        // Schedule user creation for 24 hours
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 1) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        createUser(dbUrl, dbUser, dbPassword);
                    }
                }, (hour * 60 + minute) * 60 * 1000); // Convert hours and minutes to milliseconds
            }
        }
    }
	private static void createUser(String dbUrl, String dbUser, String dbPassword) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String username = "user_" + System.currentTimeMillis();
            String email = username + "@example.com";

            String sql = "INSERT INTO users (username, email) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.executeUpdate();
                System.out.println("Created user: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
}
