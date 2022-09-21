package dao;

import java.security.Key;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import connection.Connection;
import entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UserDAO {
	
	private java.sql.Connection conn = null;
	private User user = null;
	private String sigAlgorithm = SignatureAlgorithm.HS256.getJcaName();
	private Key key = new SecretKeySpec("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9".getBytes(), this.sigAlgorithm);
	
	public UserDAO() {
		this.conn =  new Connection().GetConnection();
		this.user = new User();
	}
	
	public User Authorization(User user) {
		String sql = "SELECT * FROM users WHERE login = ? AND  password = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.first()) {
				this.user = this.ResultSetToUser(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this.user;
	}
	
	
	public String GenerateToken(int sessionTime, User user) {
		String token = Jwts.builder()
				.claim("id", user.getId())
				.claim("login", user.getName())
				.signWith(key, SignatureAlgorithm.HS256)
				.setExpiration(Date.from(LocalDateTime.now().plusMinutes(sessionTime).atZone(ZoneId.systemDefault()).toInstant())).compact();
		
		return token;
	}
	
	
	private User ResultSetToUser(ResultSet rs) {
		User user = new User();
		
		try {
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
}
