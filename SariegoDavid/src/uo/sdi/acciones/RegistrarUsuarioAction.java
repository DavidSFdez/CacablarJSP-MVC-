package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.User;
import uo.sdi.model.UserStatus;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.UserDao;

public class RegistrarUsuarioAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {
	String login = request.getParameter("login");
	String nombre = request.getParameter("nombreUsuario");
	String apellidos = request.getParameter("apellidoUsuario");
	String email = request.getParameter("emailUsuario");
	String password = request.getParameter("password");
	String password2 = request.getParameter("password2");

	if (login == null || nombre == null || apellidos == null
		|| email == null || password == null || password2 == null) {
	    Log.error("No puede haber campos vacíos");
	    request.setAttribute("mensaje", "No puede haber campos vacíos");
	    pasarValores(request, login, nombre, apellidos, email);
	    return "FRACASO";
	}
	if (login.equals("") || nombre.equals("") || apellidos.equals("")
		|| email.equals("") || password.equals("")
		|| password2.equals("")) {
	    Log.error("No puede haber campos vacíos");
	    request.setAttribute("mensaje", "No puede haber campos vacíos");
	    pasarValores(request, login, nombre, apellidos, email);
	    return "FRACASO";
	}

	UserDao ud = PersistenceFactory.newUserDao();

	if (ud.findByLogin(login) != null){
	    Log.error("Ya existe un usuario con dicho login");
	    request.setAttribute("mensaje", "Ya existe un usuario con dicho login");
	    pasarValores(request, login, nombre, apellidos, email);
	    return "FRACASO";
	}

	if (!password.equals(password2)){
	    Log.error("Las contraseñas introducidas no coinciden");
	    request.setAttribute("mensaje","Las contraseñas introducidas no coinciden");
	    pasarValores(request, login, nombre, apellidos, email);
	    return "FRACASO";
	}

	User user = new User();
	user.setLogin(login);
	user.setName(nombre);
	user.setSurname(apellidos);
	user.setEmail(email);
	user.setPassword(password);
	user.setStatus(UserStatus.ACTIVE);

	ud.save(user);
	
	request.getSession().setAttribute("user", user);
	
	Log.debug("Usuario guardado");

	return "EXITO";
    }

    private void pasarValores(HttpServletRequest request, String login,
	    String nombre, String apellidos, String email) {
	request.setAttribute("login", login);
	request.setAttribute("nombre", nombre);
	request.setAttribute("apellidos", apellidos);
	request.setAttribute("email", email);
    }

}
