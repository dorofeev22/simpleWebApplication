<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ru.medlinesoft.simplewebapplication.entity.Part"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Parts</title>
    </head>
    <body>
        <h1>Parts</h1>
            <% 
                String error = (String)request.getParameter("error");
                List<Part> parts = (ArrayList<Part>)request.getAttribute("parts");
            %>
        <p>${error}</p>
        <div>
            <table>
            <%
                for (Part part : parts) {
                    String partName = part.getVendor();
            %>
                        <tr>
                            <td>
                                ${partName}
                            </td>
                        </tr>
            <%
                }
            %>
            </table>
        </div>
    </body>
</html>
