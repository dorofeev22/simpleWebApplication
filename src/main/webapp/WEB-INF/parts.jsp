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
                String error = (String)request.getAttribute("error");
                String contextPath = request.getContextPath();
                List<PartDto> parts = (ArrayList<PartDto>)request.getAttribute("parts");
                String partNumberOrder = (String)request.getAttribute("part_number_order");
                String partNameOrder = (String)request.getAttribute("part_name_order");
            %>
        <p><%= error %></p>
        <div>
            <table>
                <tr>
                    <th>
                        <a href="<%= contextPath %>?order=<%= partNumberOrder %>&columnName=part_number">PN</a>
                    </th>
                    <th>
                        <a href="<%= contextPath %>?order=<%= partNameOrder %>&columnName=part_name">PartName</a>
                    </th>
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
