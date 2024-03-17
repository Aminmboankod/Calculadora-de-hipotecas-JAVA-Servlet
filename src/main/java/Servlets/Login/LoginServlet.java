package Servlets.Login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Hipoteca;
import models.Usuario;
import repository.Repository;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import database.dbcontext.DbContext;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger(LoginServlet.class);
	
	private static final long serialVersionUID = 1L;
	private List<Hipoteca> hipotecas;
    private Repository<Usuario> userRepository = new Repository();
    private Repository<Hipoteca> hipotecaRepository = new Repository();
    public LoginServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * Procesa una solicitud POST para iniciar sesión de usuario.
	 * Si el usuario existe, carga sus hipotecas y redirige a la página de calculadora.
	 * Si el usuario no existe, redirige a la página de inicio de sesión.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene los parámetros de la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utiliza para enviar la respuesta HTTP.
	 * @throws ServletException Si ocurre un error en el servlet durante el proceso de inicio de sesión.
	 * @throws IOException      Si ocurre un error de entrada/salida durante el proceso de inicio de sesión.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario existe;
		try {
			existe = Logearse(request, response);
			if (existe != null) {
				// Busco todas las hipotecas guardadas
            	List<Hipoteca> hipotecas = hipotecaRepository.Find(Hipoteca.class, Map.of("usuario_id", existe.getId()));
                
            	// si tiene alguna las cargo en el request
            	if (hipotecas != null)
            	{
                    request.setAttribute("hipotecas", hipotecas);
            	}
				request.getRequestDispatcher("Calculadora.jsp").forward(request, response);
			} else {
				response.sendRedirect("index.jsp");
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
	}
	
	/**
	 * Intenta iniciar sesión de un usuario utilizando los datos proporcionados en la solicitud.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene los parámetros de la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utiliza para enviar la respuesta HTTP.
	 * @return El usuario logeado si existe, o null si no existe.
	 * @throws SQLException           Si ocurre un error de SQL durante el proceso de inicio de sesión.
	 * @throws InstantiationException Si ocurre un error al instanciar un objeto durante el proceso de inicio de sesión.
	 * @throws IllegalAccessException Si se intenta acceder a un campo inaccesible durante el proceso de inicio de sesión.
	 * @throws IllegalArgumentException Si se proporciona un argumento ilegal durante el proceso de inicio de sesión.
	 * @throws InvocationTargetException Si ocurre un error al invocar un método durante el proceso de inicio de sesión.
	 * @throws NoSuchMethodException    Si no se encuentra el método solicitado durante el proceso de inicio de sesión.
	 * @throws SecurityException       Si se produce una violación de seguridad durante el proceso de inicio de sesión.
	 * @throws NoSuchFieldException    Si no se encuentra el campo solicitado durante el proceso de inicio de sesión.
	 * @throws IOException             Si ocurre un error de entrada/salida durante el proceso de inicio de sesión.
	 */
	private Usuario Logearse(HttpServletRequest request, HttpServletResponse response) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, IOException
	{
        String nombre = request.getParameter("nombre");
        String contraseña = request.getParameter("contraseña");

        Usuario usuario_logeado = new Usuario(nombre, contraseña);

        Map<String, Object> queryParams = extractQueryParams(usuario_logeado);
        try {
            Usuario existingUser = userRepository.Get(Usuario.class, queryParams);
            if (existingUser != null) {
                
            	// Creo la sesión del usuario
            	request.getSession().setAttribute("usuario", existingUser);
              
            	logger.info("Usuario "+ nombre + " logeado correctamente.");
                return existingUser;
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            logger.error("No ha podido logearse el usuario");
        }

        return null;
	}
	
	/**
	 * Extrae los parámetros de consulta de un objeto y los devuelve como un mapa de cadenas.
	 * 
	 * @param entity Objeto del que se extraen los parámetros de consulta.
	 * @return Mapa de cadenas que contiene los nombres de los campos y sus valores correspondientes.
	 */
	private <T> Map<String, Object> extractQueryParams(T entity) {
	    Class<?> entityClass = entity.getClass();
	    Map<String, Object> queryParams = new HashMap<>();

	    Field[] fields = entityClass.getDeclaredFields();
	    for (Field field : fields) {
	        field.setAccessible(true);
	        try {
	            if (field.get(entity) != null) {
	                queryParams.put(field.getName(), field.get(entity));
	            }
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        }
	    }

	    return queryParams;
	}

}
