package Servlets.Hipotecas;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import domain.Amortizacion;
import domain.CalculadoraHipoteca;
import models.Hipoteca;
import domain.ResultadoFinanciacion;
import models.Usuario;
import repository.Repository;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.*;


@WebServlet("/calcular")
public class CalculadoraHipotecaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(CalculadoraHipotecaServlet.class.getName());
	
	private CalculadoraHipoteca hipoteca;
	private Repository<Usuario> usuarioRepository = new Repository();
    private Repository<Hipoteca> hipotecaRepository = new Repository();
    public CalculadoraHipotecaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	
    /**
     * Método para manejar las peticiones POST.
     * Calcula la hipoteca y muestra el resultado en una página JSP.
     *
     * @param request  HttpServletRequest con los parámetros de la solicitud.
     * @param response HttpServletResponse para enviar la respuesta.
     * @throws ServletException si ocurre un error de servlet.
     * @throws IOException      si ocurre un error de entrada/salida.
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			CalcularHipoteca(request, response);
		} catch (ServletException | IOException | SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	
    /**
     * Calcula la hipoteca y muestra el resultado en una página JSP.
     *
     * @param request  HttpServletRequest con los parámetros de la solicitud.
     * @param response HttpServletResponse para enviar la respuesta.
     * @throws ServletException si ocurre un error de servlet.
     * @throws IOException      si ocurre un error de entrada/salida.
     * @throws SQLException             si ocurre un error de SQL.
     * @throws InstantiationException  si ocurre un error de instanciación.
     * @throws IllegalAccessException  si ocurre un error de acceso ilegal.
     * @throws IllegalArgumentException si ocurre un error de argumento ilegal.
     * @throws InvocationTargetException si ocurre un error de invocación ilegal.
     * @throws NoSuchMethodException si no se encuentra el método.
     * @throws SecurityException si ocurre un error de seguridad.
     * @throws NoSuchFieldException si no se encuentra el campo.
     */
    private void CalcularHipoteca(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        String importeStr = request.getParameter("importe");
        String interesStr = request.getParameter("interes");
        String plazoStr = request.getParameter("plazo");
       
        // Obteniendo la dirección IP del usuario
        String ipAddress = request.getRemoteAddr();
        
        if (importeStr != null && interesStr != null && plazoStr != null) {
            double importe = Double.parseDouble(importeStr);
            double interes = Double.parseDouble(interesStr);
            int plazo = Integer.parseInt(plazoStr);
       
	        // Comrobar si el usuario está logeado
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
	        if (usuario != null)
	        {	        	
	        	logger.info("Se va a realizar un estudio de hipoteca con los siguientes importes: "+ 
	        							" Importe: " + importe + 
	        							" Interes: " + interes +
	        							" Plazo: " + plazo +
	        							" Por el usuario: " + usuario.getNombre() + " desde: " + ipAddress );
	        	
	            Map<String, Object> queryParams = extractQueryParams(usuario);
	        	Usuario usuario_encontrado = usuarioRepository.Get( Usuario.class, queryParams  );
	        	
	        	if (usuario_encontrado != null)
	        	{
		        	// Si está logeado, obtener el id y guardar un registro de Hipoteca en la base de datos con el importe, interes, plazo y el id del usuario 
		            hipoteca = new CalculadoraHipoteca(importe, interes, plazo, usuario_encontrado.getId());
		            Hipoteca obj = new Hipoteca(hipoteca);
		            
		            boolean exito = hipotecaRepository.Save(obj);
		            if (!exito)
		            {
		            	logger.error("No se ha podido guardar la hipoteca recien estudiada del usuario "+ usuario_encontrado.getNombre() + " desde: " + ipAddress);
		            } else {
		            	List<Hipoteca> hipotecas = hipotecaRepository.Find(Hipoteca.class, Map.of("usuario_id", usuario_encontrado.getId()));
		            	// si tiene alguna las cargo en el request
		            	if (hipotecas != null)
		            	{
		                    request.setAttribute("hipotecas", hipotecas);
		            	}
		            }
	        	}
	            
	        }
	        else
	        {
	            // posteriormente muestro el calculo así:
	            hipoteca = new CalculadoraHipoteca(importe, interes, plazo);	
	        	logger.info("Se va a realizar un estudio de hipoteca con los siguientes importes: "+ 
						" Importe: " + importe + 
						" Interes: " + interes +
						" Plazo: " + plazo + " desde: " + ipAddress);
	        }
	        
			// Busco todas las hipotecas guardadas

            
            
            hipoteca.CalculoFinanciacion();
            ResultadoFinanciacion resultado = hipoteca.Resultado;
            List<Amortizacion> amortizacionHipoteca = hipoteca.AmortizacionHipoteca;
            request.setAttribute("resultado", resultado);
            request.setAttribute("amortizacionHipoteca", amortizacionHipoteca);
            
            request.getRequestDispatcher("Calculadora.jsp").forward(request, response);

        } 
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
