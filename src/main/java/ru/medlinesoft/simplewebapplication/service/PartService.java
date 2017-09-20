package ru.medlinesoft.simplewebapplication.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import ru.medlinesoft.simplewebapplication.dto.PartDto;
import ru.medlinesoft.simplewebapplication.entity.Part;
import ru.medlinesoft.simplewebapplication.model.PartParameters;
import ru.medlinesoft.simplewebapplication.repository.PartRepository;

/**
 * Сервис работы с данными.
 */
public class PartService {

    public List<PartDto> findDtoParts(PartParameters parameters) 
            throws ClassNotFoundException, ParseException {
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

}
