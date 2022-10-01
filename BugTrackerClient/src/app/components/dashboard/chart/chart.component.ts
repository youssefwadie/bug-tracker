import {Component, Input, OnInit} from '@angular/core';
import {ChartConfiguration, ChartData} from "chart.js";

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {

  @Input()
  category: string;

  @Input()
  chartData: ChartData<'pie', number[], string>;

  @Input()
  chartOptions: ChartConfiguration<'pie', number[], string>['options'];

  constructor() {
  }

  ngOnInit(): void {
  }

}
