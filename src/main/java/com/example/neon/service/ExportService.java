package com.example.neon.service;

import com.example.neon.model.UserEntry;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExportService {

    public void generateExcel(HttpServletResponse response, List<UserEntry> entries) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Records");
            
            // Explicitly using org.apache.poi.ss.usermodel.Row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Roll No");
            header.createCell(1).setCellValue("Name");
            header.createCell(2).setCellValue("Frontend IP");
            header.createCell(3).setCellValue("Backend IP");

            int rowIdx = 1;
            for (UserEntry entry : entries) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(entry.getRollNumber());
                row.createCell(1).setCellValue(entry.getAbstractName());
                row.createCell(2).setCellValue(entry.getIpv4Frontend());
                row.createCell(3).setCellValue(entry.getIpv4Backend());
            }
            workbook.write(response.getOutputStream());
        }
    }

    public void generatePdf(HttpServletResponse response, List<UserEntry> entries) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        
        document.add(new Paragraph("Network Configuration Report"));
        
        // Note: PdfPTable does not use the "Row" class, so there is no conflict here
        PdfPTable table = new PdfPTable(4);
        table.setSpacingBefore(10);
        table.addCell("Roll No"); 
        table.addCell("Name"); 
        table.addCell("Frontend IP"); 
        table.addCell("Backend IP");

        for (UserEntry entry : entries) {
            table.addCell(entry.getRollNumber());
            table.addCell(entry.getAbstractName());
            table.addCell(entry.getIpv4Frontend());
            table.addCell(entry.getIpv4Backend());
        }
        document.add(table);
        document.close();
    }
}