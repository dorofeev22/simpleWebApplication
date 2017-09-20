package ru.medlinesoft.simplewebapplication.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.medlinesoft.simplewebapplication.dto.PartDto;
import ru.medlinesoft.simplewebapplication.entity.ReferenceFieldsName;
import ru.medlinesoft.simplewebapplication.model.PartParameters;
import ru.medlinesoft.simplewebapplication.service.PartService;

/**
 * Сервлет для получения данных Part из базы и вывода на страницу.
 */
@WebServlet(urlPatterns = {"/"})
public class PartServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doRequest(request, response);
    }
    
    private void doRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String order = request.getParameter("order");
        String orderedFieldName = request.getParameter("columnName");
        PartParameters parameters = new PartParameters();
        parameters.setOrder(order);
        parameters.setOrderedFieldName(orderedFieldName);
        parameters.setSearchPartNumberInput(request.getParameter("part_number_input"));
        parameters.setSearchPartNameInput(request.getParameter("part_name_input"));
        parameters.setSearchVendorInput(request.getParameter("vendor_input"));
        parameters.setSearchQtyInput(request.getParameter("qty_input"));
        parameters.setSearchShippedAfterInput(request.getParameter("shipped_after_input"));
        parameters.setSearchShippedBeforeInput(request.getParameter("shipped_before_input"));
        parameters.setSearchReceiveAfterInput(request.getParameter("receive_after_input"));
        parameters.setSearchReceiveBeforeInput(request.getParameter("receive_before_input"));
        List<PartDto> parts = new ArrayList<>();
        try {
            parts = new PartService().findDtoParts(parameters);
        } catch (ClassNotFoundException ex) {
            request.setAttribute("error", ex.getMessage());
        } catch (ParseException ex) {
            request.setAttribute("error", ex.getMessage());
        }
        request.setAttribute("parts", parts);
        request.setAttribute("part_number_order", 
                ReferenceFieldsName.getOrderSortByActualFields(
                        orderedFieldName, "part_number_order", order));
        request.setAttribute("part_name_order", 
                ReferenceFieldsName.getOrderSortByActualFields(
                        orderedFieldName, "part_name_order", order));
        request.setAttribute("vendor_order", 
                ReferenceFieldsName.getOrderSortByActualFields(
                        orderedFieldName, "vendor_order", order));
        request.setAttribute("qty_order", 
                ReferenceFieldsName.getOrderSortByActualFields(
                        orderedFieldName, "qty_order", order));
        request.setAttribute("shipped_order", 
                ReferenceFieldsName.getOrderSortByActualFields(
                        orderedFieldName, "shipped_order", order));
        request.setAttribute("receive_order", 
                ReferenceFieldsName.getOrderSortByActualFields(
                        orderedFieldName, "receive_order", order));
        request.getRequestDispatcher("/WEB-INF/parts.jsp").forward(request, response);
    }

}
