package com.github.youssefwadie.bugtracker.dashboard;


import com.github.youssefwadie.bugtracker.dto.model.TicketReport;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.UserContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("report")
    public ResponseEntity<TicketReport> getReport() {
        User loggedInUser = UserContextHolder.get();
        return ResponseEntity.ok(dashboardService.getTicketsReport(loggedInUser.getId()));
    }
}
