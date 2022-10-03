import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "./services/auth-service/auth.service";
import {Route, Router} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'BugTrackerClient';
  authenticated = false;
  private authenticationResultSubscription: Subscription;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.authenticationResultSubscription =
      this.authService.authenticationResultEvent.asObservable().subscribe({
        next: authenticated => {
          this.authenticated = authenticated;
          if (!this.authenticated) {
            this.router.navigate(['login']);
          }
        }
      });
  }

  ngOnDestroy(): void {
    if (this.authenticationResultSubscription != null) {
      this.authenticationResultSubscription.unsubscribe();
    }
  }

}
