package controller;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import dao.UserDAO;
import entity.User;

public class UserController {
	private User user = null;
	
	public UserController() {
		super();
	}
	
	public Response Authorization(User user) {
		UserDAO userDAO = new UserDAO();
		int sessionTime = 4;
		if(user.getName() != null && user.getPassword() != null && !user.getName().isEmpty() && !user.getPassword().isEmpty()) {
			user = userDAO.Authorization(user);
			if(user.getId() != null && user.getId() > 0) {
				String token = userDAO.GenerateToken(sessionTime, user);
				if(token != null && !token.isEmpty()) {
					return Response.ok("success").status(Response.Status.ACCEPTED).cookie(new NewCookie("token", token, "/", "", "", (sessionTime*60), false)).build();
				}else {
					return Response.ok("Problema ao gerar o token").status(Response.Status.UNAUTHORIZED).build();
				}
			}else {
				return Response.ok("Usuario nao autorizado").status(Response.Status.UNAUTHORIZED).build();
			}
		}else {
			return Response.ok("Campo vazio").status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
}
