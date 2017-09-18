package ru.medlinesoft.simplewebapplication.service;

import java.util.List;
import ru.medlinesoft.simplewebapplication.entity.Part;
import ru.medlinesoft.simplewebapplication.repository.PartRepository;

/**
 * Сервис работы с данными.
 */
public class PartService {
    
    public List<Part> findParts() throws ClassNotFoundException {
        return new PartRepository().findParts();
    }
    
}
