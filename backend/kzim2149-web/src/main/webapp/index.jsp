<!DOCTYPE html>
<html>
<head>
    <title>PC Parts</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <h1>PCParts</h1>
    <table>
        <tr>
            <th>Name</th>
            <th>Producer</th>
            <th>Type</th>
            <th>Price</th>
            <th>Weight</th>
        </tr>
        <%@ page import="java.util.Collection" %>
        <%@ page import="edu.bbte.idde.kzim2149.model.PCPart" %>
        <%
            Collection<PCPart> pcparts = (Collection<PCPart>) request.getAttribute("pcparts");
            for (PCPart part : pcparts) {
        %>
        <tr>
            <td><%= part.getName() %></td>
            <td><%= part.getProducer() %></td>
            <td><%= part.getType() %></td>
            <td><%= part.getPrice() %></td>
            <td><%= part.getWeight() %></td>
        </tr>
        <% } %>
    </table>
    <a href="./logout">Logout</a>
</body>
</html>

