package ru.medlinesoft.simplewebapplication.repository;

import com.google.common.base.Strings;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import ru.medlinesoft.simplewebapplication.domain.ParameterNames;
import ru.medlinesoft.simplewebapplication.entity.Part;
import ru.medlinesoft.simplewebapplication.entity.ReferenceFieldName;
import ru.medlinesoft.simplewebapplication.model.PartParameters;

/**
 * Репозиторий методов доступа к базе данных.
 */
public class PartRepository {
    
    private Connection getConnection() throws ClassNotFoundException, IOException {
        Properties prop = new Properties();
        InputStream inputStream;
        String propFileName = "application.properties";
        inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
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
                part.setPartName(rs.getString("part_name"));
                part.setPartNumber(rs.getString("part_number"));
                part.setVendor(rs.getString("vendor"));
                part.setQty(rs.getInt("qty"));
                part.setShipped(rs.getDate("shipped"));
                part.setReceive(rs.getDate("receive"));
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
        Map<String, String> searchParameters = parameters.getSearchParameters();
        List<Object> querySearchParams = new ArrayList<>();
        if (!searchParameters.isEmpty()) {
            clauseQueryPartText.append(" WHERE ");
            createStrQueryParam(
                    searchParameters.get(ParameterNames.PART_NUMBER_INPUT), clauseQueryPartText, 
                    querySearchParams, ReferenceFieldName.part_number);
            createStrQueryParam(
                    searchParameters.get(ParameterNames.PART_NAME_INPUT), clauseQueryPartText, 
                    querySearchParams, ReferenceFieldName.part_name);
            createStrQueryParam(
                    searchParameters.get(ParameterNames.VENDOR_INPUT), clauseQueryPartText, 
                    querySearchParams, ReferenceFieldName.vendor);
            createIntQueryParam(
                    searchParameters.get(ParameterNames.QTY_INPUT), clauseQueryPartText, 
                    querySearchParams, ReferenceFieldName.qty);
            createDateQueryParam(
                    searchParameters.get(ParameterNames.SHIPPED_AFTER_INPUT), 
                    searchParameters.get(ParameterNames.SHIPPED_BEFORE_INPUT), 
                    clauseQueryPartText, querySearchParams, ReferenceFieldName.shipped);
            createDateQueryParam(
                    searchParameters.get(ParameterNames.RECEIVE_AFTER_INPUT), 
                    searchParameters.get(ParameterNames.RECEIVE_BEFORE_INPUT), 
                    clauseQueryPartText, querySearchParams, ReferenceFieldName.receive);
        }
        String queryText = 
                "SELECT part_name, part_number, vendor, qty, shipped, receive "
                + "FROM medlinesoft.part" + clauseQueryPartText.toString() + orderQueryPartText;
        PreparedStatement ps = connection.prepareStatement(queryText);
        for (Object o : querySearchParams) {
            if (o instanceof String) {
                ps.setString(querySearchParams.indexOf(o) + 1, o.toString());
            }
            if (o instanceof Integer) {
                ps.setInt(querySearchParams.indexOf(o) + 1, (Integer)o);
            }
            if (o instanceof Date) {
                ps.setDate(querySearchParams.indexOf(o) + 1, (Date)o);
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
            DateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
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
