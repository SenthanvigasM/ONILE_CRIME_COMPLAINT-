package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class CrimeController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/submitCrime")
    public String submitCrime(@RequestParam String crimeName,
                              @RequestParam String crimeType,
                              @RequestParam String crimeLocation) {
        try {
            jdbcTemplate.update("INSERT INTO crimes (crime_name, crime_type, crime_location) VALUES (?, ?, ?)",
                    crimeName, crimeType, crimeLocation);
            return "redirect:/success";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }

    @GetMapping("/success")
    public String showSuccessPage(Model model) {
        List<Map<String, Object>> orders = jdbcTemplate.queryForList("SELECT * FROM crimes");
        model.addAttribute("orders", orders);
        return "success";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        return "error";
    }
}

