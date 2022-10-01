import {Component, OnInit} from '@angular/core';
import {DashboardService} from "../../services/dashboard.service";
import {TicketsReport} from "../../model/tickets.report";
import {LegendPosition} from "@swimlane/ngx-charts/lib/common/types/legend.model";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  report = new TicketsReport();
  showDoughnut = false;
  view: [number, number] = [500, 250];
  legendPosition: LegendPosition = 'right' as LegendPosition;

  constructor(private dashboardService: DashboardService) {
  }

  ngOnInit(): void {
    this.dashboardService.getReport().subscribe(report => {
      console.table(report);
      this.report = report;
    });
  }

}
