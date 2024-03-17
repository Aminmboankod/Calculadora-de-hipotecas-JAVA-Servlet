package Servlets.Login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Usuario;
import repository.Repository;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import database.dbcontext.DbContext;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Repository<Usuario> userRepository = new Repository();
	private static final Logger logger = LogManager.getLogger(RegistroServlet.class);
    public RegistroServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RegistrarUsuario(request, response);
	}

	/**
	 * Registra un nuevo usuario en el sistema.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene los parámetros de la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utiliza para enviar la respuesta HTTP.
	 * @throws ServletException Si ocurre un error en el servlet durante el registro del usuario.
	 * @throws IOException      Si ocurre un error de entrada/salida durante el registro del usuario.
	 */
    private void RegistrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String contraseña = request.getParameter("contraseña");

        if (nombre == null || contraseña == null) {
            response.sendRedirect("error.jsp");
            logger.error("No se han podido obtener los datos de registro");
            return;
        }

        Usuario nuevoUsuario = new Usuario( nombre, contraseña);
        
        try {
        	
            boolean exito = userRepository.Save(nuevoUsuario);
            if (exito) {
            	logger.info("Se ha registrado correctamente");
                response.sendRedirect("Calculadora.jsp");
            } else {
            	logger.error("No ha podido registrarse");
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.sendRedirect("error.jsp");
        }
        
    }

}
