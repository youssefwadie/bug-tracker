import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../services/user-service/user.service";
import {User} from "../../../model/user";
import {PageEvent} from "@angular/material/paginator";
import {ActivatedRoute, Router} from "@angular/router";
import {Subscription} from "rxjs";
import {AppConstants} from "../../../constants/app-constants";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {UserEditComponent} from "./user-edit/user-edit.component";

@Component({
  selector: 'app-user-management',
  templateUrl: './user.management.component.html',
  styleUrls: ['./user.management.component.css']
})
export class UserManagementComponent implements OnInit {
  users = new Array<User>();
  usersCount = 0;
  action: string;
  selectedUser: User;
  readonly displayedColumns: string[] = ["fullName", "email", "role", "edit", "delete"];
  readonly pageSize = UserService.USERS_PER_PAGE;
  pageNumber = 1;

  queryParamsSubscription: Subscription;

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private router: Router,
              private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.processQueryParams();
  }

  private processQueryParams(): void {
    this.queryParamsSubscription = this.route.queryParams.subscribe((params) => {
      const id = params['id'];
      const page = +params['page'];
      const refresh = params['refresh'];
      this.action = params['action'];

      if (id != null) {
        this.userService.findById(+id).subscribe((fetchedUser) => {
          this.selectedUser = fetchedUser;
          this.open();
        });
      } else if (!isNaN(page)) {
        if (refresh != false) {
          this.loadPageContent(page);
        }
      } else {
        this.loadPageContent(this.pageNumber);
      }
    });
  }

  private loadPageContent(pageNumber: number): void {
    this.userService.listByPage(pageNumber).subscribe({
      next: response => {
        const projectCount = Number(response.headers.get(AppConstants.TOTAL_COUNT_HEADER_NAME));
        if (!isNaN(projectCount)) {
          this.usersCount = projectCount;
          const maximumPageSize = Math.ceil(this.usersCount / this.pageSize);
          if (pageNumber > maximumPageSize) {
            this.pageNumber = maximumPageSize + 1;
          } else {
            this.pageNumber = pageNumber;
          }
        }

        const responseBody = response.body;
        if (responseBody != null) this.users = responseBody;
      }
    })
  }


  open() {
    const modalRef = this.modalService.open(UserEditComponent);
    const component = modalRef.componentInstance as UserEditComponent;
    component.user = this.selectedUser;
    modalRef.result.then(
      (submitted: boolean) => {
        this.router.navigate([], {
          queryParams: {"page": this.pageNumber, "refresh": submitted}
        });
      }, (submitted: boolean) => {
        submitted = Boolean(submitted);
        this.router.navigate([], {
          queryParams: {"page": this.pageNumber, "refresh": submitted}
        });
      }
    );

  }


  viewUser(id: number) {
    console.log(`Viewing ... ${id}`);
  }

  onPageChange(pageEvent: PageEvent) {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {"page": pageEvent.pageIndex + 1},
    });
  }

  editUser(user: User) {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {"action": "edit", "id": user.id, "page": this.pageNumber},
    });
  }

  deleteUser(user: User) {
    console.log(`Deleting .... ${user.id}`);
  }
}
