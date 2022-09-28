import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../../services/auth.service";
import {HttpErrorResponse} from "@angular/common/http";
import {SimpleResponse} from "../../../model/ResponseBody";

@Component({
  selector: 'app-verify-email',
  templateUrl: './verify-email.component.html',
  styleUrls: ['./verify-email.component.css']
})
export class VerifyEmailComponent implements OnInit {
  email = '';
  subject = 'Confirm Your E-mail';
  infoMessage = '';
  errorMessage = '';


  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe({
      next: params => {
        const email = params['email'];
        if (email) {
          this.email = email;
        }
      }
    });
  }

  send(): void {
    this.authService.resendVerificationToken(this.email).subscribe({
      next: next => {
        this.infoMessage = next.message
      }, error: err => {
        if (err.status === 400) {
          const error = err.error as SimpleResponse;
          const message = error.message;
          if (message === 'invalid email') {
            this.errorMessage = message;
          }
        }
      }
    })
  }

  login()
    :
    void {
    this.router.navigate(['login']);
  }
}
