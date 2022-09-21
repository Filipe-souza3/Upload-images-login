package dao;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import entity.File;
import entity.User;

public class FileDAO {
	private Connection conn = null;
	private File file = null;
	private String pathFile = "http://localhost:8080/UploadFiles/resources/imgs/";
	private String url  = getClass().getClassLoader().getResource("").toString().split("(UploadFiles[\\/aA-zZ-]{1,})$")[0];
	private String pathFolder = this.url.split("(file:\\/)")[1] + "UploadFiles/resources/imgs/";
	
	
	public FileDAO() {
		this.conn = new connection.Connection().GetConnection();
		this.file = new File();
	}
	
	
	
	public File Insert(String name, String extensionFile, InputStream fileInputStream, String fileMetaData) {
		
		String fileName = fileInputStream.hashCode()+fileMetaData;
		String pathFile = this.pathFolder + fileName;
		
		String sql = "INSERT INTO files (name, path, datetime) VALUES(?,?,now()) RETURNING *";
		
		if(!this.SaveFile(pathFile, extensionFile, fileInputStream)) {
			return this.file;
		}
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, name);
			pstmt.setString(2, fileName);
			ResultSet rs = pstmt.executeQuery();
			if(rs.first()) {
				this.file = this.ResultSetToFile(rs,false);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.file;
	}
	

	public File GetFilesList() {
		String sql = "SELECT * FROM files ORDER BY id";
		List<File> files = new ArrayList<File>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				File file = new File();
				file = this.ResultSetToFile(rs, true);
				files.add(file);
			}
			this.file.setFiles(files);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.file;
	}
	
	
	public File FindFileById(int fileId) {
		
		String sql = "SELECT * FROM files WHERE id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, fileId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.first()) {
				
				this.file = this.ResultSetToFile(rs, true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.file;
	}
	
	
	public File DeleteFile(int fileId) {
		String sql  = "SELECT path FROM files WHERE id = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, fileId);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.first()) {
				String filePath = rs.getString("path");
				Path path = Paths.get(this.pathFolder + filePath);
				
				if(Files.deleteIfExists(path)) {
					sql = "DELETE FROM files WHERE id = ? RETURNING *";
					pstmt = conn.prepareStatement(sql,  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					pstmt.setInt(1, fileId);
					
					rs = pstmt.executeQuery();
					if(rs.next()) {
						this.file = this.ResultSetToFile(rs, false);
					}
				}
			}
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return this.file;
	}
	
	
	
	public String login(User user) {
		
		return "";
	}
	
	////////////////////////////////BASE 64////////////////////////////////////
	public File FindFileByIdBase64(int fileId) {
		
		String sql = "SELECT * FROM files WHERE id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, fileId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.first()) {
				
				this.file.setId(rs.getInt("id"));
				this.file.setPath(rs.getString("path"));
				this.file.setName(rs.getString("name"));
				this.file.setDatetime(rs.getTimestamp("datetime"));
				this.file.setDatetimeString(rs.getTimestamp("datetime"));
				
				java.io.File file = new java.io.File(this.file.getPath());
				byte[] content = Files.readAllBytes(file.toPath());
				String enconded64 = Base64.getEncoder().encodeToString(content);
				this.file.setPath(enconded64);
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return this.file;
	}
	
	
	public File GetFilesListBase64() {
		String sql = "SELECT * FROM files ORDER BY id";
		List<File> files = new ArrayList<File>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				File file = new File();
				file = this.ResultSetToFile(rs, false);
				java.io.File fileBase64 = new java.io.File(file.getPath());
				byte[] content = Files.readAllBytes(fileBase64.toPath());
				String enconded64 = Base64.getEncoder().encodeToString(content);
				file.setPath(enconded64);
				files.add(file);
			}
			this.file.setFiles(files);
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return this.file;
	}
	
	public File DeleteFileBase64(int fileId) {
		String sql  = "SELECT path FROM files WHERE id = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, fileId);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.first()) {
				String filePath = rs.getString("path");
				Path path = Paths.get(filePath);
				String fileEncoded = this.EncodeBase64File(filePath);
				
				if(Files.deleteIfExists(path)) {
					sql = "DELETE FROM files WHERE id = ? RETURNING *";
					pstmt = conn.prepareStatement(sql,  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					pstmt.setInt(1, fileId);
					
					rs = pstmt.executeQuery();
					if(rs.next()) {
						this.file = this.ResultSetToFile(rs, true);
						this.file.setPath(fileEncoded);
					}
				}
			}
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return this.file;
	}
	////////////////////////////////BASE 64////////////////////////////////////

	public boolean CreateDirectory() {
		java.nio.file.Path path = Paths.get(this.pathFolder);

		if(!Files.exists(path)) {
			try {
				Files.createDirectory(path);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	
	private boolean SaveFile(String path, String  extensionFile, InputStream fileInputStream) {
		
		java.io.File file = new java.io.File(path);
		BufferedImage bi;
		try {
			bi = ImageIO.read(fileInputStream);
			//redimencionar
			BufferedImage bim = new BufferedImage(100, 100, bi.getType());
			bim.getGraphics().drawImage(bi, 0, 0, 100, 100, null);
			//
			ImageIO.write(bim, extensionFile, file);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	private File ResultSetToFile(ResultSet rs, boolean withPath) throws SQLException {
		File file = new File();

		file.setId(rs.getInt("id"));
		file.setName(rs.getString("name"));
		if(withPath == true) {
			file.setPath(this.pathFile + rs.getString("path"));			
		}else {
			file.setPath(this.pathFolder + rs.getString("path"));
		}
		file.setDatetime(rs.getTimestamp("datetime"));
		file.setDatetimeString(rs.getTimestamp("datetime"));
		return file;
	}

	
	private String EncodeBase64File(String pathFile) {
		java.io.File fileBase64 = new java.io.File(pathFile);
		byte[] content;
		String enconded64 = "";
		try {
			content = Files.readAllBytes(fileBase64.toPath());
			enconded64 = Base64.getEncoder().encodeToString(content);
			file.setPath(enconded64);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return enconded64;
	}
	
	
	private String GetExtension(String fileName) {
		String[] fileSplit = fileName.split("(.*)[.]");
		return fileSplit[1];
	}
	
}
