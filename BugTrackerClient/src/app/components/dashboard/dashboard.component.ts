import {Component, OnInit} from '@angular/core';
import {DashboardService} from "../../services/dashboard-service/dashboard.service";
import {ReportEntry, TicketsReport} from "../../model/tickets.report";
import {ChartConfiguration, ChartData} from "chart.js";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  report = new TicketsReport();

  statusChartData: ChartData<'pie', number[], string>;
  typeChartData: ChartData<'pie', number[], string>;
  priorityChartData: ChartData<'pie', number[], string>;

  chartOptions: ChartConfiguration<'pie', number[], string>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'right',
      }
    },
  };


  constructor(private dashboardService: DashboardService) {
  }

  ngOnInit(): void {
    this.dashboardService.getReport().subscribe(report => {
      this.report = report;

      this.typeChartData = this.extractChartData(this.report.type);
      this.priorityChartData = this.extractChartData(this.report.priority);
      this.statusChartData = this.extractChartData(this.report.status);
    });

  }

  private extractChartData(reportEntries: Array<ReportEntry>): ChartData<'pie', number[], string> {
    return {
      labels: reportEntries.map(entry => entry.name),
      datasets: [{
        data: reportEntries.map(entry => entry.value)
      }]
    };
  }

}
