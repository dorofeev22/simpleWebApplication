package ru.medlinesoft.simplewebapplication.servlet;

import com.google.common.base.Strings;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.medlinesoft.simplewebapplication.dto.PartDto;
import ru.medlinesoft.simplewebapplication.domain.ParameterNames;
import ru.medlinesoft.simplewebapplication.entity.ReferenceFieldName;
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
        List<PartDto> parts = new ArrayList<>();
        String order = request.getParameter("order");
        String orderedFieldName = request.getParameter("columnName");
        Map<String, String> searchParameters = new HashMap<>();
        putParameter(request, ParameterNames.PART_NUMBER_INPUT, searchParameters);
        putParameter(request, ParameterNames.PART_NAME_INPUT, searchParameters);
        putParameter(request, ParameterNames.VENDOR_INPUT, searchParameters);
        putParameter(request, ParameterNames.QTY_INPUT, searchParameters);
        putParameter(request, ParameterNames.SHIPPED_AFTER_INPUT, searchParameters);
        putParameter(request, ParameterNames.SHIPPED_BEFORE_INPUT, searchParameters);
        putParameter(request, ParameterNames.RECEIVE_AFTER_INPUT, searchParameters);
        putParameter(request, ParameterNames.RECEIVE_BEFORE_INPUT, searchParameters);
        PartParameters partParameters = 
                new PartParameters(order, orderedFieldName, searchParameters);
        try {
            parts = new PartService().findDtoParts(partParameters);
        } catch (ClassNotFoundException ex) {
            request.setAttribute("error", ex.getMessage());
        } catch (ParseException ex) {
            request.setAttribute("error", ex.getMessage());
        }
        request.setAttribute("parts", parts);
        request.setAttribute("part_number_order", 
                ReferenceFieldName.getOrderSortByActualFields(
                        orderedFieldName, "part_number_order", order));
        request.setAttribute("part_name_order", 
                ReferenceFieldName.getOrderSortByActualFields(
                        orderedFieldName, "part_name_order", order));
        request.setAttribute("vendor_order", 
                ReferenceFieldName.getOrderSortByActualFields(
                        orderedFieldName, "vendor_order", order));
        request.setAttribute("qty_order", 
                ReferenceFieldName.getOrderSortByActualFields(
                        orderedFieldName, "qty_order", order));
        request.setAttribute("shipped_order", 
                ReferenceFieldName.getOrderSortByActualFields(
                        orderedFieldName, "shipped_order", order));
        request.setAttribute("receive_order", 
                ReferenceFieldName.getOrderSortByActualFields(
                        orderedFieldName, "receive_order", order));
        request.setAttribute("search_params", searchParameters);
        request.getRequestDispatcher("/WEB-INF/parts.jsp").forward(request, response);
    }
    
    private void putParameter(
            HttpServletRequest request, 
            String parameterName, 
            Map<String, String> searchParameters) {
        String searchPartNumberInput = request.getParameter(parameterName);
        if (!Strings.isNullOrEmpty(searchPartNumberInput)) {
            searchParameters.put(parameterName, searchPartNumberInput);
        }
    }

}
