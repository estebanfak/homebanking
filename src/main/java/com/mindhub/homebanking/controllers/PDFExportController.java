package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.services.PDFGeneratorService;
import com.mindhub.homebanking.services.implement.PDFGeneratorServiceImplement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PDFExportController {

    private final PDFGeneratorServiceImplement pdfGeneratorServiceImplement;

    public PDFExportController(PDFGeneratorServiceImplement pdfGeneratorServiceImplement) {
        this.pdfGeneratorServiceImplement = pdfGeneratorServiceImplement;
    }

    @GetMapping("/pdf/generate")
    public void generatePDF(HttpServletResponse response) throws IOException {


        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";


        response.setHeader(headerKey, headerValue);
        this.pdfGeneratorServiceImplement.export(response);
    }
}
