package ru.medlinesoft.simplewebapplication.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.medlinesoft.simplewebapplication.dto.PartDto;
import ru.medlinesoft.simplewebapplication.entity.ReferenceFieldsName;
import ru.medlinesoft.simplewebapplication.service.PartService;

/**
 * Сервлет для получения данных Part из базы и вывода на страницу.
 */
@WebServlet(urlPatterns = {"/"})
public class PartServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String order = request.getParameter("order");
        String fieldsName = request.getParameter("columnName");
        List<PartDto> parts = new ArrayList<>();
        try {
            parts = new PartService().findDtoParts(order, fieldsName);
        } catch (ClassNotFoundException ex) {
            request.setAttribute( "error", ex.getMessage());
        }
        request.setAttribute("parts", parts);
        request.setAttribute("part_number_order", 
                ReferenceFieldsName.getOrderSortByActualFields(
                        fieldsName, "part_number_order", order));
        request.setAttribute("part_name_order", 
                ReferenceFieldsName.getOrderSortByActualFields(
                        fieldsName, "part_name_order", order));
        request.setAttribute("vendor_order", 
                ReferenceFieldsName.getOrderSortByActualFields(
                        fieldsName, "vendor_order", order));
        request.setAttribute("qty_order", 
                ReferenceFieldsName.getOrderSortByActualFields(
                        fieldsName, "qty_order", order));
        request.setAttribute("shipped_order", 
                ReferenceFieldsName.getOrderSortByActualFields(
                        fieldsName, "shipped_order", order));
        request.setAttribute("receive_order", 
                ReferenceFieldsName.getOrderSortByActualFields(
                        fieldsName, "receive_order", order));
        request.getRequestDispatcher("/WEB-INF/parts.jsp").forward(request, response);
    }
    
}
