<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ru.medlinesoft.simplewebapplication.dto.PartDto"%>
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
                List<PartDto> parts = (ArrayList<PartDto>)request.getAttribute("parts");
            %>
        <p><%= error %></p>
        <div>
            <table>
                <tr>
                    <th>
                        <a href="/simpleWebApplication?order=desc">PN</a>
                    </th>
                    <th>PartName</th>
                    <th>Vendor</th>
                    <th>Qty</th>
                    <th>Shipped</th>
                    <th>Received</th>
                </tr>
            <%
                for (PartDto dto : parts) {
                    String partNumber = dto.getPartNumber();
                    String partName = dto.getPartName();
                    String vendor = dto.getVendor();
                    String qty = dto.getQty();
                    String shipped = dto.getShipped();
                    String receive = dto.getReceive();
            %>
                        <tr>
                            <td>
                                <a><%= partNumber %></a>
                            </td>
                            <td>
                                <a><%= partName %></a>
                            </td>
                            <td>
                                <a><%= vendor %></a>
                            </td>
                            <td>
                                <a><%= qty %></a>
                            </td>
                            <td>
                                <a><%= shipped %></a>
                            </td>
                            <td>
                                <a><%= receive %></a>
                            </td>
                        </tr>
            <%
                }
            %>
            </table>
        </div>
    </body>
</html>
