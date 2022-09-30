import {Component, Input, OnInit} from '@angular/core';
import {faComputer, faTicket, faToolbox, faUserShield, faUsersRectangle} from "@fortawesome/free-solid-svg-icons";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  faComputer = faComputer;
  faTicket = faTicket;
  faUserShield = faUserShield;
  faUsersRectangle = faUsersRectangle

  @Input() active: string;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.authService.logout().subscribe({
        next: () => {
          this.router.navigate(['login']);
        }, error: () => {
          this.router.navigate(['login']);
        }
      }
    );
  }

  navigateToAdministration() {
    this.router.navigate(['administration']);
  }

  navigateToTickets() {
    this.router.navigate(['tickets']);
  }

  navigateToDashboard() {
    this.router.navigate(['dashboard']);
  }

  navigateToProjects() {

  }
}
