import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserLogin} from 'src/app/model/user';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../services/auth-service/auth.service";
import {map, Subscription} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {SimpleResponse} from "../../model/response.body";

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

  constructor(private router: Router, private route: ActivatedRoute, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.registrationSub = this.route.paramMap.pipe(map(() => window.history.state)).subscribe(history => {
      if (history.hasOwnProperty(0)) {
        this.infoMessage = history[0];
      }
    });

  }

  onLogin(): void {
    this.infoMessage = '';
    this.authService.login(this.user).subscribe({
      next: () => {
        this.router.navigate(['']);
      }, error: (errorResponse: HttpErrorResponse) => {
        this.validLogin = false;
        const error = errorResponse.error as SimpleResponse;
        if (error) {
          this.errorMessage = error.message;
        } else {
          this.errorMessage = 'Invalid email or password';
        }
      }
    });
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
  }

}
