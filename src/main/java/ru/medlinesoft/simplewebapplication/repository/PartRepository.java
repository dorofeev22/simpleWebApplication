package ru.medlinesoft.simplewebapplication.repository;

import com.google.common.base.Strings;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import ru.medlinesoft.simplewebapplication.domain.SearchParameterName;
import ru.medlinesoft.simplewebapplication.entity.Part;
import ru.medlinesoft.simplewebapplication.entity.ReferenceFieldName;
import ru.medlinesoft.simplewebapplication.model.PartParameters;
import ru.medlinesoft.simplewebapplication.utils.Utils;

/**
 * Репозиторий методов доступа к базе данных.
 */
public class PartRepository {
    
    private Connection getConnection() throws ClassNotFoundException, IOException {
        Properties prop = new Properties();
        String propFileName = "application.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
        Class.forName("org.postgresql.Driver");
        String userName = prop.getProperty("user");
        String password = prop.getProperty("password");;
        String url = prop.getProperty("url");
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            return connection;
        } catch (SQLException ex) {
            return null;
        }
    }

    public List<Part> findParts(PartParameters parameters) 
            throws ClassNotFoundException, ParseException, IOException {
        List<Part> parts = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement ps = getQuery(parameters, connection);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Part part = new Part();
                part.setPartName(rs.getString(ReferenceFieldName.part_name.name()));
                part.setPartNumber(rs.getString(ReferenceFieldName.part_number.name()));
                part.setVendor(rs.getString(ReferenceFieldName.vendor.name()));
                part.setQty(rs.getInt(ReferenceFieldName.qty.name()));
                part.setShipped(rs.getDate(ReferenceFieldName.shipped.name()));
                part.setReceive(rs.getDate(ReferenceFieldName.receive.name()));
                parts.add(part);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            
        }
        return parts;
    }
    
    private PreparedStatement getQuery(
            PartParameters parameters, Connection connection) throws SQLException, ParseException {
        String order = parameters.getOrder();
        String orderedFieldName = parameters.getOrderedFieldName();
        String orderQueryPartText = order != null ? " ORDER BY " + orderedFieldName + " " + order : "";
        StringBuilder clauseQueryPartText = new StringBuilder();
        Map<SearchParameterName, String> searchParameters = parameters.getSearchParameters();
        List<Object> querySearchParams = new ArrayList<>();
        // Строим часть запроса с условиями фильтрации и кладем значения для фильтрации в список объектов
        if (!searchParameters.isEmpty()) {
            clauseQueryPartText.append(" WHERE ");
            createStrQueryParam(searchParameters.get(SearchParameterName.part_number_input), clauseQueryPartText, 
                    querySearchParams, ReferenceFieldName.part_number);
            createStrQueryParam(searchParameters.get(SearchParameterName.part_name_input), clauseQueryPartText, 
                    querySearchParams, ReferenceFieldName.part_name);
            createStrQueryParam(searchParameters.get(SearchParameterName.vendor_input), clauseQueryPartText, 
                    querySearchParams, ReferenceFieldName.vendor);
            createIntQueryParam(searchParameters.get(SearchParameterName.qty_input), clauseQueryPartText, 
                    querySearchParams, ReferenceFieldName.qty);
            createDateQueryParam(searchParameters.get(SearchParameterName.shipped_after_input), 
                    searchParameters.get(SearchParameterName.shipped_before_input), 
                    clauseQueryPartText, querySearchParams, ReferenceFieldName.shipped);
            createDateQueryParam(searchParameters.get(SearchParameterName.receive_after_input), 
                    searchParameters.get(SearchParameterName.receive_before_input), 
                    clauseQueryPartText, querySearchParams, ReferenceFieldName.receive);
        }
        // Строим текст запроса с условием сортировки и фильтрации
        StringBuilder queryText = new StringBuilder("SELECT ");
        queryText.append(String.join(", ", ReferenceFieldName.getFieldNames())).append(" FROM medlinesoft.part ")
                .append(clauseQueryPartText.toString()).append(orderQueryPartText);
        PreparedStatement ps = connection.prepareStatement(queryText.toString());
        // устанавливаем в PreparedStatement параметры
        for (Object o : querySearchParams) {
            if (o instanceof String) {
                ps.setString(querySearchParams.indexOf(o) + 1, o.toString());
            }
            if (o instanceof Integer) {
                ps.setInt(querySearchParams.indexOf(o) + 1, (Integer)o);
            }
            if (o instanceof java.sql.Date) {
                ps.setDate(querySearchParams.indexOf(o) + 1, (java.sql.Date)o);
            }
        }
        return ps;
    }

    private void createStrQueryParam(
            String searchInput, StringBuilder clauseQueryPartText, 
            List<Object> querySearchParams, ReferenceFieldName fieldName) {
        if (!Strings.isNullOrEmpty(searchInput)) {
            clauseQueryPartText.append(!querySearchParams.isEmpty() ? " AND " : "")
                    .append("LOWER(").append(fieldName.name()).append(") LIKE ? ");
            searchInput = "%" + searchInput.toLowerCase() + "%";
            querySearchParams.add(searchInput);
        }
    }

    private void createDateQueryParam(
            String searchInputAfter, String searchInputBefore, StringBuilder clauseQueryPartText, 
            List<Object> querySearchParams, ReferenceFieldName fieldName) throws ParseException {
        if (!Strings.isNullOrEmpty(searchInputAfter) && !Strings.isNullOrEmpty(searchInputBefore)) {
            clauseQueryPartText.append(!querySearchParams.isEmpty() ? " AND " : "")
                    .append(fieldName.name()).append(" BETWEEN ? AND ? ");
            DateFormat df = Utils.getDateFormat();
            querySearchParams.add(new java.sql.Date(df.parse(searchInputAfter).getTime()));
            querySearchParams.add(new java.sql.Date(df.parse(searchInputBefore).getTime()));
        }
    }

    private void createIntQueryParam(
            String searchInput, StringBuilder clauseQueryPartText, 
            List<Object> querySearchParams, ReferenceFieldName fieldName) {
        if (!Strings.isNullOrEmpty(searchInput)) {
            clauseQueryPartText.append(!querySearchParams.isEmpty() ? " AND " : "")
                    .append(fieldName.name()).append(" >= ? ");
            querySearchParams.add(Integer.valueOf(searchInput));
        }
    }

}
