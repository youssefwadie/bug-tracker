import {Component, Input, OnInit} from '@angular/core';
import {faComputer, faTicket, faToolbox} from "@fortawesome/free-solid-svg-icons";
import {Router} from "@angular/router";

@Component({
  selector: 'app-vertical-nav',
  templateUrl: './vertical-nav.component.html',
  styleUrls: ['./vertical-nav.component.css']
})
export class VerticalNavComponent implements OnInit {
  faComputer = faComputer;
  faTicket = faTicket;
  faToolbox = faToolbox;

  @Input() active: string;
  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  logout(): void {
    console.log('logging out...');
  }

  navigateToAdministration() {
    this.router.navigate(['administration'])
  }

  navigateToTickets() {
    this.router.navigate(['tickets'])
  }

  navigateToDashboard() {
    this.router.navigate(['dashboard']);
  }
}
