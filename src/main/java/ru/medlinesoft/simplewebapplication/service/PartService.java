package ru.medlinesoft.simplewebapplication.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import ru.medlinesoft.simplewebapplication.domain.OrderParameterNames;
import ru.medlinesoft.simplewebapplication.dto.PartDto;
import ru.medlinesoft.simplewebapplication.entity.Part;
import ru.medlinesoft.simplewebapplication.entity.ReferenceFieldName;
import ru.medlinesoft.simplewebapplication.entity.ReferenceSortOrder;
import ru.medlinesoft.simplewebapplication.model.PartParameters;
import ru.medlinesoft.simplewebapplication.repository.PartRepository;

/**
 * Сервис работы с данными.
 */
public class PartService {

    public List<PartDto> findDtoParts(PartParameters parameters) 
            throws ClassNotFoundException, ParseException, IOException {
        List<PartDto> dtos = new ArrayList<>();
        for (Part part : new PartRepository().findParts(parameters)) {
            dtos.add(toDto(part));
        }
        return dtos;
    }

    private PartDto toDto(Part part) {
        PartDto dto = new PartDto();
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        dto.setPartName(part.getPartName());
        dto.setPartNumber(part.getPartNumber());
        dto.setQty(String.valueOf(part.getQty()));
        dto.setReceive(df.format(part.getReceive()));
        dto.setShipped(df.format(part.getShipped()));
        dto.setVendor(part.getVendor());
        return dto;
    }

    public ReferenceSortOrder getSortOrderForField(
            String fieldsName, OrderParameterNames parameter, String actualOrder) {
        if (fieldsName == null || actualOrder == null) {
            return ReferenceSortOrder.asc;
        }
        ReferenceFieldName referenceFieldsName = ReferenceFieldName.createReferenceFieldsName(fieldsName);
        switch (referenceFieldsName) {
            case part_name:
                return parameter.equals(OrderParameterNames.part_name_order) 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc;
            case part_number:
                return parameter.equals(OrderParameterNames.part_number_order) 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc;
            case vendor:
                return parameter.equals(OrderParameterNames.vendor_order) 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc;
            case qty:
                return parameter.equals(OrderParameterNames.qty_order) 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc;
            case shipped:
                return parameter.equals(OrderParameterNames.shipped_order) 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc;
            case receive:
                return parameter.equals(OrderParameterNames.receive_order) 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc;
            default:
                return null;
        }
    }

}
