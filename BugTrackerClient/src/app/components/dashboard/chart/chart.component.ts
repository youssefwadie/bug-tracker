import {Component, Input, OnInit} from '@angular/core';
import {ReportEntry} from "../../../model/tickets.report";
import {LegendPosition} from "@swimlane/ngx-charts/lib/common/types/legend.model";

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {

  @Input()
  content: Array<ReportEntry>;


  @Input()
  showDoughnut: boolean;

  @Input()
  category: string;

  @Input()
  view: [number, number];

  @Input()
  legendPosition: LegendPosition;

  constructor() {
  }

  ngOnInit(): void {
  }

}
