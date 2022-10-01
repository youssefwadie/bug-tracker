package com.github.youssefwadie.bugtracker.dto.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class TicketReport {
    private List<ReportEntry> status = Collections.emptyList();
    private List<ReportEntry> type = Collections.emptyList();
    private List<ReportEntry> priority = Collections.emptyList();

    public static record ReportEntry(String name, long value) { }

}
