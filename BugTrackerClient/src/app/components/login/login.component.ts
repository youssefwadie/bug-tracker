import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserLogin} from 'src/app/model/user';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../services/auth-service/auth.service";
import {map, Subscription} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  user: UserLogin = new UserLogin();
  validLogin = true;
  errorMessage: string;
  infoMessage: string;

  registrationSub: Subscription;
  authenticationMessageSubscription: Subscription;

  constructor(private router: Router, private route: ActivatedRoute, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.registrationSub = this.route.paramMap.pipe(map(() => window.history.state)).subscribe(history => {
      if (history.hasOwnProperty(0)) {
        this.infoMessage = history[0];
      }
    });

    this.authenticationMessageSubscription = this.authService.authenticationMessageEvent.subscribe({
      next: (msg: string) => {
        this.errorMessage = msg;
      }
    });
  }

  onLogin(): void {
    this.infoMessage = '';
    this.authService.authenticate(this.user);
    this.authService.authenticationResultEvent.subscribe({
        next: (result: boolean) => {
          if (result) {
            this.router.navigate(['']);
          } else {
            this.validLogin = false;
          }
        }
      }
    );
  }

  forgotPassword(): void {
    this.router.navigate(['reset'])
  }

  register(): void {
    this.router.navigate(['register']);
  }

  ngOnDestroy(): void {
    if (this.registrationSub != null) {
      this.registrationSub.unsubscribe();
    }
    if (this.authenticationMessageSubscription != null) {
      this.authenticationMessageSubscription.unsubscribe();
    }
  }

}
