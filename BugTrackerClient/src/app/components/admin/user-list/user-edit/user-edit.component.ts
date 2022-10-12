import {Component, OnInit} from '@angular/core';
import {Role, User} from "../../../../model/user";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../../services/user-service/user.service";

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {
  user: User;
  formUser: User;
  errorMessage = '';
  pageNumber = 1;
  availableRoles: Array<Role>;
  userRole: Role;


  // validation
  validFullName = true;
  validEmail = true;
  validRole = false;
  readonly invalidFullNameMessage = "Full name must contain the first and surname parts, separated by space";
  readonly invalidEmailMessage = "Please enter a valid email";
  readonly invalidRoleMessage = "User's role must be set";

  private readonly validEmailPattern = "^[a-z0-9._%+-]+@[a-z0-9.-]+(\.[a-z]{2,4})+$";

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    public activeModal: NgbActiveModal) {

    this.availableRoles = [
      Role.ROLE_ADMIN,
      Role.ROLE_DEVELOPER
    ];
  }

  ngOnInit(): void {
    this.userRole = this.user.role;
    this.formUser = Object.assign({}, this.user);
    this.validForm();
  }


  // just a convenient method
  validForm() {
    this.checkIfFullNameIsValid();
    this.checkIfEmailIsValid();
    this.checkIfRoleIsValid();
  }

  checkIfFullNameIsValid(): void {
    if (this.user.fullName.length !== 0 && this.formUser.fullName === this.user.fullName) {
      this.validFullName = true;
      return;
    }

    if (this.formUser.fullName) {
      this.formUser.fullName = this.formUser.fullName.trim();
    }

    if (!this.formUser.fullName || this.formUser.fullName.length === 0) {
      this.validFullName = false;
      return;
    }

    const indexOfWhiteSpace = this.formUser.fullName.indexOf(' ');
    if (indexOfWhiteSpace === -1) {
      this.validFullName = false;
      return;
    }

    if (this.formUser.fullName.length < 8) {
      this.validFullName = false;
      return;
    }

    this.validFullName = true;
  }

  checkIfEmailIsValid(): void {
    if (!this.formUser.email.match(this.validEmailPattern)) {
      this.validEmail = false;
    }
  }

  checkIfRoleIsValid(): void {
    this.validRole = this.userRole != null;
  }

  onSubmit() {
    this.checkIfFullNameIsValid();
    this.checkIfRoleIsValid();
    this.checkIfRoleIsValid();

    if (this.validFullName && this.validEmail && this.validRole) {
      this.formUser.role = this.userRole;
      this.userService.update(this.formUser).subscribe({
        next: () => {
          this.activeModal.close(true);
        }
      });
    }
  }
}
