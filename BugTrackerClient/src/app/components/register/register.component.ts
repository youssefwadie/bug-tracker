import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Router} from "@angular/router";
import {UserRegistration} from "../../model/UserRegistration";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, OnDestroy {

  private emailPattern = /^[a-z0-9._%+-]+@[a-z0-9.-]+(\.[a-z]{2,4})+$/g;
  firstName = '';
  lastName = '';
  email = '';
  password = '';
  repeatedPassword = '';

  validRegistration = true;
  errorMessage = '';
  private registrationSubscription: Subscription;

  constructor(private router: Router) {
  }


  ngOnInit(): void {
  }

  onRegister(): void {
    this.validRegistration = this.validFormData();
    console.log(this.validRegistration);
    if (this.validRegistration) {
      const registration: UserRegistration = {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
        password: this.password
      };
      console.log('sending...');
      console.log(registration);
    }
  }

  public login(): void {
    this.router.navigate(['login']);
  }

  private validFormData(): boolean {
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

  ngOnDestroy(): void {
    if (this.registrationSubscription != null) {
      this.registrationSubscription.unsubscribe();
    }
  }

}
