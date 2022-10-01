import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserRegistrationRequest} from "../../model/user.registration.request";
import {HttpErrorResponse} from "@angular/common/http";
import {RegistrationService} from "../../services/registration.service";
import {InvalidDataResponse} from "../../model/response.body";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  private emailPattern = /^[a-z0-9._%+-]+@[a-z0-9.-]+(\.[a-z]{2,4})+$/g;
  firstName = '';
  lastName = '';
  email = '';
  password = '';
  repeatedPassword = '';

  validRegistration = true;
  errorMessage = '';

  constructor(private router: Router, private registrationService: RegistrationService) {
  }


  ngOnInit(): void {
  }

  onRegister(): void {
    this.validRegistration = this.validFormData();
    if (!this.validRegistration) {
      return;
    }

    const registration: UserRegistrationRequest = {
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      password: this.password
    };
    this.registrationService.register(registration).subscribe({
      next: next => {
        this.router.navigate(['login'], {state: [next.message]});
      }, error: (err: HttpErrorResponse) => {
        const response = err.error as InvalidDataResponse;
        const firstError = Object.keys(response.invalidData)[0] as string;
        const firstErrorDescription = Object.values(response.invalidData)[0] as string;
        if (firstError === 'email') {
          this.setErrorMessage(`${firstError} is ${firstErrorDescription}, please try a different email.`);
        } else {
          this.setErrorMessage(`${firstError}: ${firstErrorDescription}.`);
        }
      }
    });
  }

  public login(): void {
    this.router.navigate(['login']);
  }

  private validFormData(): boolean {
    if (this.firstName.length === 0) {
      this.setErrorMessage("Please enter your first name");
      return false;
    }
    if (this.lastName.length === 0) {
      this.setErrorMessage("Please enter your last name");
      return false;
    }

    if (!this.email.match(this.emailPattern)) {
      this.setErrorMessage("Please enter a valid email");
      return false;
    }
    if (this.password.length < 8) {
      this.setErrorMessage("Password must be at least 8 characters");
      return false;
    }
    if (this.password !== this.repeatedPassword) {
      this.setErrorMessage("The passwords don't match");
      return false;
    }
    return true;
  }


  private setErrorMessage(errorMessage: string): void {
    this.validRegistration = false;
    this.errorMessage = errorMessage;
  }

}
