<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="domain.ResultadoFinanciacion" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="domain.Amortizacion" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Usuario" %>
<%@ page import="models.Hipoteca" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Calculadora de Hipoteca</title>
    <!-- Latest compiled and minified CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .form-group label {
            font-weight: bold;
        }
        .table-responsive {
            margin-top: 2rem;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Hipotecas Calculator</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="#">Inicio</a>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <% Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
                        if (usuario != null) { %>
                            <span class="nav-link">Bienvenido, <%= usuario.getNombre() %></span>
                        <% } %>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <form action="calcular" method="post" class="mb-4">
                    <div class="form-group">
                        <label for="importe">Importe de la hipoteca:</label>
                        <input type="number" id="importe" name="importe" required class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="interes">Tipo de interés (% anual):</label>
                        <input type="number" id="interes" name="interes" step="0.01" required class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="plazo">Plazo de amortización (meses):</label>
                        <input type="number" id="plazo" name="plazo" required class="form-control">
                    </div>
                    <button type="submit" class="btn btn-primary">Calcular</button>
                </form>
					<div class="form-group">
					    <label for="hipoteca">Selecciona una hipoteca existente:</label>
					    <select id="hipoteca" class="form-control">
					        <%
					        List<Hipoteca> hipotecas = (List<Hipoteca>) request.getAttribute("hipotecas");
					        if (hipotecas != null) {
					            for (Hipoteca hipoteca : hipotecas) {
					        %>
					        <option class="list-group-item" name="hipoteca_id" value="<%= hipoteca.getId() %>">
					            Importe: <%= hipoteca.getImporte() %>, 
					            Interés: <%= hipoteca.getInteres() %>%,
					            Plazo: <%= hipoteca.getPlazo() %> meses
					        </option>
					        <%
					            }
					        }
					        %>
					    </select>
					</div>
                
                <%
                ResultadoFinanciacion resultado = (ResultadoFinanciacion) request.getAttribute("resultado");
                DecimalFormat formato = new DecimalFormat("#.##");
                List<Amortizacion> amortizacionHipoteca = (List<Amortizacion>) request.getAttribute("amortizacionHipoteca");
                if (resultado != null) {
                %>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Pagos</th>
                                <th>Principal</th>
                                <th>Cuota</th>
                                <th>Total Intereses</th>
                                <th>Total Pagos</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><%= resultado.Pagos %></td>
                                <td><%= formato.format(resultado.Principal) %></td>
                                <td><%= formato.format(resultado.Cuota) %></td>
                                <td><%= formato.format(resultado.TotalIntereses) %></td>
                                <td><%= formato.format(resultado.TotalPagos) %></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <% } %>
                <%
                if (amortizacionHipoteca != null) {
                %>
                <h2>Tabla de Amortización</h2>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Pago Número</th>
                            <th>Capital Pendiente</th>
                            <th>Capital Amortizado</th>
                            <th>Intereses</th>
                            <th>Cuota</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        for (Amortizacion amortizacion : amortizacionHipoteca) {
                        %>
                        <tr>
                            <td><%= amortizacion.getPagoNumero() %></td>
                            <td><%= formato.format(amortizacion.getCapitalPendiente()) %></td>
                            <td><%= formato.format(amortizacion.getCapitalAmortizado()) %></td>
                            <td><%= formato.format(amortizacion.getIntereses()) %></td>
                            <td><%= formato.format(amortizacion.getCuota()) %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <% } %>
            </div>
        </div>
        <script>
        var select = document.getElementById("hipoteca");

     // Agregar un event listener para detectar cambios en el select
     select.addEventListener("change", function() {
         // Obtener el valor seleccionado
         var hipotecaSeleccionada = select.value;

         // Configurar la solicitud Fetch
         fetch('recuperar?id='+hipotecaSeleccionada, {
             method: 'GET'
         })
         .then(response => {
             // Verificar el estado de la respuesta
             if (!response.ok) {
                 throw new Error('Error al enviar la solicitud');
             }
             return response.text();
         })
         .then(data => {
             // Manejar la respuesta si es necesario
             console.log(data);
         })
         .catch(error => {
             console.error('Error:', error);
         });
     });
    
</script>
    </div>
</body>
</html>
