

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Amortizacion;
import models.Hipoteca;
import models.ResultadoFinanciacion;

import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class Hipotecas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Hipoteca hipoteca;

    public Hipotecas() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("Calculadora.jsp");
	    // Obtener la lista de amortización
	    //List<Amortizacion> amortizacionHipoteca = hipoteca.AmortizacionHipoteca;

	    // Guardar la lista en el request
	    //request.setAttribute("amortizacionHipoteca", amortizacionHipoteca);

	    // Redirigir al JSP para mostrar la tabla de amortización
	    //request.getRequestDispatcher("tabla-amortizacion.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");
	    
	    if (action != null && action.equals("calcular")) {
	        String importeStr = request.getParameter("importe");
	        String interesStr = request.getParameter("interes");
	        String plazoStr = request.getParameter("plazo");
	        
	        if (importeStr != null && interesStr != null && plazoStr != null) {
	            double importe = Double.parseDouble(importeStr);
	            double interes = Double.parseDouble(interesStr);
	            int plazo = Integer.parseInt(plazoStr);
	            
	            hipoteca = new Hipoteca(importe, interes, plazo);
	            hipoteca.CalculoFinanciacion();
	            ResultadoFinanciacion resultado = hipoteca.Resultado;
	            List<Amortizacion> amortizacionHipoteca = hipoteca.AmortizacionHipoteca;
	            request.setAttribute("resultado", resultado);
	            request.setAttribute("amortizacionHipoteca", amortizacionHipoteca);
	            request.getRequestDispatcher("Calculadora.jsp").forward(request, response);
	            //response.sendRedirect("Calculadora.jsp");
	        }
	    }
	}

}
