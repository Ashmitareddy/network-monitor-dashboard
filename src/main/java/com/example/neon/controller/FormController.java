package com.example.neon.controller;

import com.example.neon.model.UserEntry;
import com.example.neon.repository.UserEntryRepository;
import com.example.neon.service.ExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable; // Added for Delete functionality

import java.io.IOException;
import java.util.List;

@Controller
public class FormController {

    @Autowired
    private UserEntryRepository repository;

    @Autowired
    private ExportService exportService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("userEntry", new UserEntry());
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<UserEntry> entries = repository.findAll();
        
        // Calculate Stats
        long totalLogs = entries.size();
        
        // FIX: Normalize to UpperCase so 23eg and 23EG count as 1 unique student
        long uniqueRolls = entries.stream()
                                  .map(entry -> entry.getRollNumber().toUpperCase())
                                  .distinct()
                                  .count();
                                  
        String lastUpdate = entries.isEmpty() ? "No Data" : "Live";

        model.addAttribute("entries", entries);
        model.addAttribute("totalLogs", totalLogs);
        model.addAttribute("uniqueRolls", uniqueRolls);
        model.addAttribute("lastUpdate", lastUpdate);
        
        return "dashboard";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute UserEntry userEntry) {
        repository.save(userEntry);
        return "redirect:/dashboard";
    }

    // NEW FEATURE: Delete entry by ID
    @PostMapping("/delete/{id}")
    public String deleteEntry(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=Network_Records.xlsx");
        exportService.generateExcel(response, repository.findAll());
    }

    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Network_Records.pdf");
        exportService.generatePdf(response, repository.findAll());
    }
}
