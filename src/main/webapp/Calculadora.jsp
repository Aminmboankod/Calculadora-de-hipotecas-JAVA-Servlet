<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.ResultadoFinanciacion" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="models.Amortizacion" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Calculadora de Hipoteca</title>
</head>
<body>
    <h1>Calculadora de Hipoteca</h1>
    <form action="calcular" method="post">
        <label for="importe">Importe de la hipoteca:</label>
        <input type="number" id="importe" name="importe" required><br><br>
        
        <label for="interes">Tipo de interés (% anual):</label>
        <input type="number" id="interes" name="interes" step="0.01" required><br><br>
        
        <label for="plazo">Plazo de amortización (meses):</label>
        <input type="number" id="plazo" name="plazo" required><br><br>
        
        <input type="hidden" name="action" value="calcular">
        <input type="submit" value="Calcular">
    </form>
    
    <%
    ResultadoFinanciacion resultado = (ResultadoFinanciacion) request.getAttribute("resultado");
    DecimalFormat formato = new DecimalFormat("#.##");
    List<Amortizacion> amortizacionHipoteca = (List<Amortizacion>) request.getAttribute("amortizacionHipoteca");
    if (resultado != null) {
    %>
    <div>
    <h2>Resultado de la financiación:</h2>
    <table border="1">
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
    <div>
    <a href="?veramort=true">Ver cuadro de amortización</a>
    </div>
    <%
    if (amortizacionHipoteca != null)
    {    %>
        <h1>Tabla de Amortización</h1>
    <table border="1">
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
</body>
</html>
