<%-- 
    Document   : Message
    Created on : 6/04/2015, 03:39:12 PM
    Author     : luisa.suarezz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <center>
        <h3><%=request.getAttribute("Message")%> </h3>
    </center>
    </body>
</html>
