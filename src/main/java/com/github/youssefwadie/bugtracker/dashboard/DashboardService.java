package com.github.youssefwadie.bugtracker.dashboard;

import com.github.youssefwadie.bugtracker.dto.model.TicketReport;
import com.github.youssefwadie.bugtracker.model.TicketPriority;
import com.github.youssefwadie.bugtracker.model.TicketStatus;
import com.github.youssefwadie.bugtracker.model.TicketType;
import com.github.youssefwadie.bugtracker.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DashboardService {
    private final TicketRepository ticketRepository;

    public TicketReport getTicketsReport(Long submitterId) {
        return TicketReport.builder()
                .type(getTypeEntries(submitterId))
                .priority(getPriorityEntries(submitterId))
                .status(getStatusEntries(submitterId))
                .build();

    }

    private List<TicketReport.ReportEntry> getTypeEntries(Long submitterId) {
        final TicketType[] types = TicketType.values();
        List<TicketReport.ReportEntry> typeEntries = new ArrayList<>(types.length);
        for (TicketType type : types) {
            long count = ticketRepository.countBySubmitterIdAndType(submitterId, type);
            typeEntries.add(new TicketReport.ReportEntry(type.getDescription(), count));
        }
        return typeEntries;
    }

    private List<TicketReport.ReportEntry> getStatusEntries(Long submitterId) {
        final TicketStatus[] statuses = TicketStatus.values();
        List<TicketReport.ReportEntry> statusEntries = new ArrayList<>(statuses.length);
        for (TicketStatus status : statuses) {
            long count = ticketRepository.countBySubmitterIdAndStatus(submitterId, status);
            statusEntries.add(new TicketReport.ReportEntry(status.getDescription(), count));
        }
        return statusEntries;
    }


    private List<TicketReport.ReportEntry> getPriorityEntries(Long submitterId) {
        final TicketPriority[] priorities = TicketPriority.values();
        List<TicketReport.ReportEntry> priorityEntries = new ArrayList<>(priorities.length);
        for (TicketPriority priority : priorities) {
            long count = ticketRepository.countBySubmitterIdAndPriority(submitterId, priority);
            priorityEntries.add(new TicketReport.ReportEntry(priority.getDescription(), count));
        }
        return priorityEntries;
    }
}
