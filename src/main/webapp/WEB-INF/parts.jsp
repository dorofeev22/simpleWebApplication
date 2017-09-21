<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="ru.medlinesoft.simplewebapplication.dto.PartDto"%>
<%@ page import="ru.medlinesoft.simplewebapplication.model.PartParameters"%>
<%@ page import="ru.medlinesoft.simplewebapplication.domain.SearchParameterName"%>


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
                String vendorOrder = (String)request.getAttribute("vendor_order");
                String qtyOrder = (String)request.getAttribute("qty_order");
                String shippedOrder = (String)request.getAttribute("shipped_order");
                String receivedOrder = (String)request.getAttribute("receive_order");
                Map<SearchParameterName, String> searchParams = 
                        (HashMap<SearchParameterName, String>)request.getAttribute("search_params");
                // соберем текущие параметры поиска (фильтрации) в url, для сортировки отобранных данных
                StringBuilder urlParams = new StringBuilder();
                for (SearchParameterName paramName : searchParams.keySet()) {
                    urlParams.append("&").append(paramName.name()).append("=").append(searchParams.get(paramName));
                }
            %>
        <p><%= error != null ? error : "" %></p>
        <p><%= !searchParams.isEmpty() ? "Filter by " + searchParams : "No filter" %></p>
        <div>
            <form action="<%= contextPath %>">
                <table>
                    <tr>
                        <td>PN</td>
                        <td><input type="text" name="part_number_input"/></td>
                    </tr>
                    <tr>
                        <td>Part Name</td>
                        <td><input type="text" name="part_name_input"/></td>
                    </tr>
                    <tr>
                        <td>Vendor</td>
                        <td><input type="text" name="vendor_input"/></td>
                    </tr>
                    <tr>
                        <td>Qty</td>
                        <td><input type="number" name="qty_input"/></td>
                    </tr>
                    <tr>
                        <td>Shipped</td>
                        <td>
                            after<input type="date" name="shipped_after_input"/>
                             before<input type="date" name="shipped_before_input"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Receive</td>
                        <td>
                            after<input type="date" name="receive_after_input"/>
                             before<input type="date" name="receive_before_input"/>
                        </td>
                    </tr>
                </table>
                <input type="submit" value="Filter"/>
            </form>
        </div>
        <div>
            <table>
                <tr>
                    <th>
                        <a href="<%= contextPath %>?columnName=part_number&order=<%= partNumberOrder + urlParams %>">PN</a>
                    </th>
                    <th>
                        <a href="<%= contextPath %>?columnName=part_name&order=<%= partNameOrder + urlParams %>">PartName</a>
                    </th>
                    <th>
                        <a href="<%= contextPath %>?columnName=vendor&order=<%= vendorOrder + urlParams %>">Vendor</a>
                    </th>
                    <th>
                        <a href="<%= contextPath %>?columnName=qty&order=<%= qtyOrder + urlParams %>">Qty</a>
                    </th>
                    <th>
                        <a href="<%= contextPath %>?columnName=shipped&order=<%= shippedOrder + urlParams %>">Shipped</a>
                    </th>
                    <th>
                        <a href="<%= contextPath %>?columnName=receive&order=<%= receivedOrder + urlParams %>">Received</a>
                    </th>
                </tr>
            <%
                for (PartDto dto : parts) {
            %>
                <tr>
                    <td>
                        <a><%= dto.getPartNumber() %></a>
                    </td>
                    <td>
                        <a><%= dto.getPartName() %></a>
                    </td>
                    <td>
                        <a><%= dto.getVendor() %></a>
                    </td>
                    <td>
                        <a><%= dto.getQty() %></a>
                    </td>
                    <td>
                        <a><%= dto.getShipped() %></a>
                    </td>
                    <td>
                        <a><%= dto.getReceive() %></a>
                    </td>
                </tr>
            <%
                }
            %>
            </table>
        </div>
    </body>
</html>
