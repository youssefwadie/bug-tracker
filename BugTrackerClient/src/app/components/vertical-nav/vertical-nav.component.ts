import { Component, OnInit } from '@angular/core';
import {faComputer, faTicket, faToolbox} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-vertical-nav',
  templateUrl: './vertical-nav.component.html',
  styleUrls: ['./vertical-nav.component.css']
})
export class VerticalNavComponent implements OnInit {
  faComputer = faComputer;
  faTicket = faTicket;
  faToolbox = faToolbox;

  active = 'dashboard';
  constructor() { }

  ngOnInit(): void {
  }

}
