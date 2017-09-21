package ru.medlinesoft.simplewebapplication.servlet;

import com.google.common.base.Strings;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.medlinesoft.simplewebapplication.domain.OrderParameterName;
import ru.medlinesoft.simplewebapplication.dto.PartDto;
import ru.medlinesoft.simplewebapplication.domain.SearchParameterName;
import ru.medlinesoft.simplewebapplication.entity.ReferenceSortOrder;
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
        PartService partService = new PartService();
        List<PartDto> parts = new ArrayList<>();
        String currentOrder = request.getParameter("order");
        String orderedFieldName = request.getParameter("columnName");
        Map<SearchParameterName, String> searchParameters = new HashMap<>();
        for (SearchParameterName parameterName : SearchParameterName.values()) {
            String searchPartNumberInput = request.getParameter(parameterName.name());
            if (!Strings.isNullOrEmpty(searchPartNumberInput)) {
                searchParameters.put(parameterName, searchPartNumberInput);
            }
        }
        PartParameters partParameters = 
                new PartParameters(currentOrder, orderedFieldName, searchParameters);
        try {
            parts = partService.findDtoParts(partParameters);
        } catch (ClassNotFoundException | ParseException ex) {
            request.setAttribute("error", ex.getMessage());
        }
        request.setAttribute("parts", parts);
        for (OrderParameterName orderParameterName : OrderParameterName.values()) {
            ReferenceSortOrder order = 
                    partService.getSortOrderForField(orderedFieldName, orderParameterName, currentOrder);
            request.setAttribute(orderParameterName.name(), order.name());
        }
        request.setAttribute("search_params", searchParameters);
        request.getRequestDispatcher("/WEB-INF/parts.jsp").forward(request, response);
    }

}
