package org.example.controller;

import org.springframework.stereotype.Service;

public interface ContactService {
    void loadContactsFromCsv(String filePath);
}
