package Servlets.Hipotecas;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Hipoteca;
import repository.Repository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.*;

import domain.Amortizacion;
import domain.CalculadoraHipoteca;
import domain.ResultadoFinanciacion;

@WebServlet("/recuperar")
public class RecuperarHipotecaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(RecuperarHipotecaServlet.class.getName());
	private CalculadoraHipoteca hipoteca;
    private Repository<Hipoteca> hipotecaRepository = new Repository();
    public RecuperarHipotecaServlet() {
        super();
    }

    
    
    /**
     * Procesa una solicitud GET para mostrar el resultado de la calculadora de hipotecas.
     * 
     * @param request  Objeto HttpServletRequest que contiene los parámetros de la solicitud HTTP.
     * @param response Objeto HttpServletResponse que se utiliza para enviar la respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet durante el proceso de cálculo de la hipoteca.
     * @throws IOException      Si ocurre un error de entrada/salida durante el proceso de cálculo de la hipoteca.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
            String hipoteca_id = request.getParameter("id");
			CalculadoraHipoteca h = CalcularHipoteca(request, response, hipoteca_id);
			if (h != null)
			{
	            h.CalculoFinanciacion();
	            ResultadoFinanciacion resultado = h.Resultado;
	            List<Amortizacion> amortizacionHipoteca = h.AmortizacionHipoteca;
	            request.setAttribute("resultado", resultado);
	            request.setAttribute("amortizacionHipoteca", amortizacionHipoteca);
	            
	            request.getRequestDispatcher("Calculadora.jsp").forward(request, response);
	            
			}
		} catch (ServletException | IOException | SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	
	/**
	 * Procesa una solicitud POST para calcular una hipoteca y mostrar los resultados.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene los parámetros de la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utiliza para enviar la respuesta HTTP.
	 * @throws ServletException Si ocurre un error en el servlet durante el proceso de cálculo de la hipoteca.
	 * @throws IOException      Si ocurre un error de entrada/salida durante el proceso de cálculo de la hipoteca.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
            String hipoteca_id = request.getParameter("id");
			CalcularHipoteca(request, response, hipoteca_id);
		} catch (ServletException | IOException | SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Calcula una hipoteca y devuelve una instancia de CalculadoraHipoteca.
	 * 
	 * @param request   Objeto HttpServletRequest que contiene los parámetros de la solicitud HTTP.
	 * @param response  Objeto HttpServletResponse que se utiliza para enviar la respuesta HTTP.
	 * @param parameter Parámetro que contiene el ID de la hipoteca.
	 * @return Instancia de CalculadoraHipoteca si se calcula correctamente, de lo contrario null.
	 * @throws ServletException          Si ocurre un error en el servlet durante el proceso de cálculo de la hipoteca.
	 * @throws IOException               Si ocurre un error de entrada/salida durante el proceso de cálculo de la hipoteca.
	 * @throws SQLException              Si ocurre un error de SQL durante el proceso de cálculo de la hipoteca.
	 * @throws NumberFormatException    Si ocurre un error al convertir el ID de la hipoteca a un entero.
	 * @throws InstantiationException   Si ocurre un error al instanciar un objeto durante el proceso de cálculo de la hipoteca.
	 * @throws IllegalAccessException   Si se intenta acceder a un campo inaccesible durante el proceso de cálculo de la hipoteca.
	 * @throws IllegalArgumentException Si se proporciona un argumento ilegal durante el proceso de cálculo de la hipoteca.
	 * @throws InvocationTargetException Si ocurre un error al invocar un método durante el proceso de cálculo de la hipoteca.
	 * @throws NoSuchMethodException     Si no se encuentra el método solicitado durante el proceso de cálculo de la hipoteca.
	 * @throws SecurityException        Si se produce una violación de seguridad durante el proceso de cálculo de la hipoteca.
	 * @throws NoSuchFieldException     Si no se encuentra el campo solicitado durante el proceso de cálculo de la hipoteca.
	 */
    private CalculadoraHipoteca CalcularHipoteca(HttpServletRequest request, HttpServletResponse response, String parameter) throws ServletException, IOException, SQLException, NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {


    	int id = Integer.parseInt(parameter);
        List<Hipoteca> hipoteca = hipotecaRepository.Find(Hipoteca.class, Map.of("id", id));
        
        if (hipoteca !=null)
        {
        	Hipoteca hipoteca_seleccionada = hipoteca.get(0);
        
	        if (hipoteca_seleccionada != null) {
	            
		        // posteriormente muestro el calculo así:
	        	CalculadoraHipoteca h = new CalculadoraHipoteca(hipoteca_seleccionada.getImporte(), hipoteca_seleccionada.getInteres(), hipoteca_seleccionada.getPlazo());	
		        
	            return h;
	
	        } else {
				logger.warn("No se ha encontrado una coincidencia");
	        	return null;
	        }
	        
        } else {
			logger.warn("No se ha encontrado una coincidencia");
        	return null;
        }
    }
}
