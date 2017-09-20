package ru.medlinesoft.simplewebapplication.repository;

import com.google.common.base.Strings;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import ru.medlinesoft.simplewebapplication.entity.Part;
import ru.medlinesoft.simplewebapplication.entity.ReferenceFieldsName;
import ru.medlinesoft.simplewebapplication.model.PartParameters;

/**
 * Репозиторий методов доступа к базе данных.
 */
public class PartRepository {
    
    private Connection getConnection() throws ClassNotFoundException {
        // TODO create application.properties;
        Class.forName("org.postgresql.Driver");
        String userName = "postgres";
        String password = "W#nlt^c0w";
        String url = "jdbc:postgresql://localhost/medlinesoft";
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            return connection;
        } catch (SQLException ex) {
            return null;
        }
    }

    public List<Part> findParts(PartParameters parameters) throws ClassNotFoundException, ParseException {
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
        if (searchParameters != null) {
            String searchPartNumberInput = searchParameters.get("part_number_input");
            String searchPartNameInput = searchParameters.get("part_name_input");
            String searchVendorInput = searchParameters.get("vendor_input");
            String searchQtyInput = searchParameters.get("qty_input");
            String searchShippedAfterInput = searchParameters.get("shipped_after_input");
            String searchShippedBeforeInput = searchParameters.get("shipped_before_input");
            String searchReceiveAfterInput = searchParameters.get("receive_after_input");
            String searchReceiveBeforeInput = searchParameters.get("receive_before_input");
            clauseQueryPartText.append(" WHERE ");
            if (!Strings.isNullOrEmpty(searchPartNumberInput)) {
                clauseQueryPartText.append("LOWER(").append(ReferenceFieldsName.part_number.name()).append(") LIKE ? ");
                searchPartNumberInput = "%" + searchPartNumberInput.toLowerCase() + "%";
                querySearchParams.add(searchPartNumberInput);
            }
            if (!Strings.isNullOrEmpty(searchPartNameInput)) {
                if (!querySearchParams.isEmpty()) {
                    clauseQueryPartText.append(" AND ");
                }
                clauseQueryPartText.append("LOWER(").append(ReferenceFieldsName.part_name.name()).append(") LIKE ? ");
                searchPartNameInput = "%" + searchPartNameInput.toLowerCase() + "%";
                querySearchParams.add(searchPartNameInput);
            }
            if (!Strings.isNullOrEmpty(searchVendorInput)) {
                if (!querySearchParams.isEmpty()) {
                    clauseQueryPartText.append(" AND ");
                }
                clauseQueryPartText.append("LOWER(").append(ReferenceFieldsName.vendor.name()).append(") LIKE ? ");
                searchVendorInput = "%" + searchVendorInput.toLowerCase() + "%";
                querySearchParams.add(searchVendorInput);
            }
            if (!Strings.isNullOrEmpty(searchQtyInput)) {
                if (!querySearchParams.isEmpty()) {
                    clauseQueryPartText.append(" AND ");
                }
                clauseQueryPartText.append(ReferenceFieldsName.qty.name()).append(" >= ? ");
                querySearchParams.add(Integer.valueOf(searchQtyInput));
            }
            if (!Strings.isNullOrEmpty(searchShippedAfterInput) 
                    && !Strings.isNullOrEmpty(searchShippedBeforeInput)) {
                if (!querySearchParams.isEmpty()) {
                    clauseQueryPartText.append(" AND ");
                }
                clauseQueryPartText.append(ReferenceFieldsName.shipped.name()).append(" BETWEEN ? AND ? ");
                querySearchParams.add(new java.sql.Date(getDateFormat().parse(searchShippedAfterInput).getTime()));
                querySearchParams.add(new java.sql.Date(getDateFormat().parse(searchShippedBeforeInput).getTime()));
            }
            if (!Strings.isNullOrEmpty(searchReceiveAfterInput) 
                    && !Strings.isNullOrEmpty(searchReceiveBeforeInput)) {
                if (!querySearchParams.isEmpty()) {
                    clauseQueryPartText.append(" AND ");
                }
                clauseQueryPartText.append(ReferenceFieldsName.receive.name()).append(" BETWEEN ? AND ? ");
                querySearchParams.add(new java.sql.Date(getDateFormat().parse(searchReceiveAfterInput).getTime()));
                querySearchParams.add(new java.sql.Date(getDateFormat().parse(searchReceiveBeforeInput).getTime()));
            }
            if (querySearchParams.isEmpty()) {
                clauseQueryPartText = new StringBuilder();
            }
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
    
    private DateFormat getDateFormat() {
        return new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
    }

}
