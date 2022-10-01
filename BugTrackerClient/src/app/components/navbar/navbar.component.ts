import {Component, Input, OnInit} from '@angular/core';
import {faComputer, faTicket, faUserShield, faUsersRectangle} from "@fortawesome/free-solid-svg-icons";
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
  @Input() active: string;

  isAdmin = false;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.authService.getUserRole().subscribe({
      next: role => {
        this.isAdmin = (role.role === 'ADMIN');
      }
    });
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
