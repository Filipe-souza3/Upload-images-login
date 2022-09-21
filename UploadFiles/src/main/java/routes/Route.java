package routes;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.security.Key;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import controller.FileController;
import controller.UserController;
import dao.FileDAO;
import dao.UserDAO;
import entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Path("/up")
public class Route {
	@Context private UriInfo uri;
	

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Insert(
			@NotEmpty @FormDataParam("name") String name, 
			@NotEmpty @FormDataParam("file") InputStream fileInputStream, 
			@NotEmpty @FormDataParam("file") FormDataContentDisposition fileMetaData
			) throws IOException {
//		
//		return Response.ok().entity(new FileController().Insert(name,fileInputStream,fileMetaData.getFileName())).build();
		return new FileController().Insert(name, fileInputStream, fileMetaData.getFileName());
	}
	
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetFilesList() {
//		return Response.ok().entity(new FileController().GetFilesList()).build();
		return new FileController().GetFilesList();
	}
	 
	@GET
	@Path("/show/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response FindFileById(@PathParam("id") int fileId) throws SQLException, IOException {
//		return Response.ok().entity(new FileController().FindFileById(fileId)).build();
		return new FileController().FindFileById(fileId);
	}
	
	@GET
	@Path("/listBase64")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetFilesListBase64() {
		return new FileController().GetFilesListBase64();
	}
	
	
	@GET
	@Path("/showBase64/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response FindFileByIdBase64(@PathParam("id") int fileId) throws SQLException, IOException {
		return new FileController().FindFileByIdBase64(fileId);
	}
	
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public Response DeleteFile(@PathParam("id") int fileId) {
//		return Response.ok().entity(new FileDAO().DeleteFile(fileId)).build();
		return new FileController().Delete(fileId);
	}
	//////////////////////////////////////////////////
	
	@GET
	@Path("/img/{idImg}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetImg(@PathParam("idImg") int idImg) {
		
		return Response.ok("img").build();
	}
	

	@GET
	@Path("/error")
	@Produces(MediaType.APPLICATION_JSON)
	public Response show(@PathParam("contentP") String contentP) throws IOException {
//		String a  = System.getProperty("user.dir");
		entity.File file = null;
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
/*	@POST
	@Path("/log")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response log (User user) throws UnsupportedEncodingException {
		
		if(user.getName() != null && !user.getName().isEmpty()) {
			Key key = new SecretKeySpec("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9".getBytes(), SignatureAlgorithm.HS256.getJcaName());
			int sessionTime = 4;
			
			String token = Jwts.builder()
					.claim("content", "hue")
					.signWith(key, SignatureAlgorithm.HS256)
					.setExpiration(Date.from(LocalDateTime.now().plusMinutes(sessionTime).atZone(ZoneId.systemDefault()).toInstant())).compact();
			
			///para os cookies ficarem acessiveis precisa colocar false no final
			return Response.ok("success").status(Response.Status.ACCEPTED).cookie(new NewCookie("token",token, "/", "", "", (sessionTime*60), false)).build();
		}else {
			return Response.ok("error").status(Response.Status.UNAUTHORIZED).build();
		}
	}*/
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response login (User user) {
		return new UserController().Authorization(user);
	}
	
	
	
	
    @GET
    @Produces("text/html")
    public String index() throws IOException {

//    	return uri.getAbsolutePath().toString();

    	ClassLoader cl = getClass().getClassLoader();
//    	String url  = cl.getResource("").toString();
    	String url  = getClass().getClassLoader().getResource("").toString().split("(UploadFiles[\\/aA-zZ-]{1,})$")[0];
    	url = url.split("(file:\\/)")[1];
    	URL url2  = cl.getResource("/imgs/praia.jpg");
//    	String split[] = url.split("(UploadFiles)([\\/aA-zZ-]{1,})");
//    	([aA-Zz-]{1,})([\/])([a-z]{1,})([\/])$
//    	ImageIO.read(getClass().getClassLoader().getResource("praiares.jpg"));
//    	return ImageIO.read(getClass().getClassLoader().getResource("praiares.jpg")).toString();
    	return  url2.toString()+" // "+ url;
//    	return System.getProperty("user.dir");

    	

    }

}
