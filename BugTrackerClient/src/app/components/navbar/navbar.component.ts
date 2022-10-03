import {Component, OnInit} from '@angular/core';
import {faTicket, faUserShield} from "@fortawesome/free-solid-svg-icons";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../services/auth-service/auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  faTicketIcon = faTicket;
  faUserShieldIcon = faUserShield;

  isAdmin = false;

  constructor(private authService: AuthService,
              public route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.authService.roleSetEvent.subscribe({
      next: (role: string) => {
        this.isAdmin = (role === 'ADMIN');
      }
    })
  }

  logout(): void {
    this.authService.logout();
  }
}
