package com.mindhub.homebanking.services;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PDFGeneratorService {
    default void export(HttpServletResponse response) throws IOException {

    }
}
