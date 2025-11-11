package spotify.spotifylabelsspringv3.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.*;

@Controller
public class H2DatabaseTestController {
    @GetMapping("/database")
    public void home(Model model, @AuthenticationPrincipal OAuth2User principal) throws SQLException, ClassNotFoundException {
        // Load the H2 Driver (optional in newer versions)
        Class.forName("org.h2.Driver");

        // Connect to an in-memory H2 database
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");

        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE users(id INT PRIMARY KEY, name VARCHAR(255));");
        stmt.execute("INSERT INTO users VALUES(1, 'Alice'), (2, 'Bob');");

        ResultSet rs = stmt.executeQuery("SELECT * FROM users;");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " " + rs.getString("name"));
        }

        conn.close();
    }

}