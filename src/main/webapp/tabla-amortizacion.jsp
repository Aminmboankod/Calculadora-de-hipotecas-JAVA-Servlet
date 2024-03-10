<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Amortizacion" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tabla de Amortización</title>
</head>
<body>
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
            List<Amortizacion> amortizacionHipoteca = (List<Amortizacion>) request.getAttribute("amortizacionHipoteca");
            for (Amortizacion amortizacion : amortizacionHipoteca) { 
            %>
            <tr>
                <td><%= amortizacion.getPagoNumero() %></td>
                <td><%= amortizacion.getCapitalPendiente() %></td>
                <td><%= amortizacion.getCapitalAmortizado() %></td>
                <td><%= amortizacion.getIntereses() %></td>
                <td><%= amortizacion.getCuota() %></td>
            </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
