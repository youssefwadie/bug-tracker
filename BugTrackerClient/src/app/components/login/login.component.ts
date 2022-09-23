import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/User';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = new User();
  validLogin = true;
  errorMessage: string;
  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  onLogin(): void {}

  forgotPassword(): void {
    console.log('forgot password');
  }

  register(): void {
    this.router.navigate(['register']);
  }

}
