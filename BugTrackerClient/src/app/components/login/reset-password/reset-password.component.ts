import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  email = '';
  subject = 'Confirm Your E-mail';
  infoMessage = '';
  errorMessage = '';


  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService) {
  }

  ngOnInit(): void {
  }

  send(): void {
    this.authService.resendVerificationToken(this.email).subscribe({
      next: next => {
        this.infoMessage = next.message;
        this.errorMessage = '';
      }, error: err => {
        if (err.status === 400) {
          this.infoMessage = '';
          this.errorMessage = "Couldn't find any matching Email. Please try again with a different email.";
        }
      }
    })
  }

  login(): void {
    this.router.navigate(['login']);
  }
}
