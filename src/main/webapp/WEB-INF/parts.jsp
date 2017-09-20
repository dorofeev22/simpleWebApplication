<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="ru.medlinesoft.simplewebapplication.dto.PartDto"%>
<%@ page import="ru.medlinesoft.simplewebapplication.model.PartParameters"%>


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
                Map<String, String> searchParams = (HashMap<String, String>)request.getAttribute("search_params");
                StringBuilder urlParams = new StringBuilder();
                for (String key : searchParams.keySet()) {
                    urlParams.append("&").append(key).append("=").append(searchParams.get(key));
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
                <input type="submit" value="Найти"/>
            </form>
        </div>
        <div>
            <table>
                <tr>
                    <th>
                        <a href="<%= contextPath %>?order=<%= partNumberOrder %>&columnName=part_number<%= urlParams %>">PN</a>
                    </th>
                    <th>
                        <a href="<%= contextPath %>?order=<%= partNameOrder %>&columnName=part_name<%= urlParams %>">PartName</a>
                    </th>
                    <th>
                        <a href="<%= contextPath %>?order=<%= vendorOrder %>&columnName=vendor<%= urlParams %>">Vendor</a>
                    </th>
                    <th>
                        <a href="<%= contextPath %>?order=<%= qtyOrder %>&columnName=qty<%= urlParams %>">Qty</a>
                    </th>
                    <th>
                        <a href="<%= contextPath %>?order=<%= shippedOrder %>&columnName=shipped<%= urlParams %>">Shipped</a>
                    </th>
                    <th>
                        <a href="<%= contextPath %>?order=<%= receivedOrder %>&columnName=receive<%= urlParams %>">Received</a>
                    </th>
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
