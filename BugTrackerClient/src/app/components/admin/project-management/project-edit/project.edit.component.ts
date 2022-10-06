import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Project} from "../../../../model/project";
import {ActivatedRoute, Router} from "@angular/router";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {ProjectService} from "../../../../services/project-service/project.service";
import {UserService} from "../../../../services/user-service/user.service";
import {SimpleResponse} from "../../../../model/response.body";
import {HttpErrorResponse} from "@angular/common/http";
import {Subscription} from "rxjs";
import {User} from "../../../../model/user";

@Component({
  selector: 'app-project-edit',
  templateUrl: './project.edit.component.html',
  styleUrls: ['./project.edit.component.css']
})
export class ProjectEditComponent implements OnInit, OnDestroy {
  project: Project;
  formProject: Project;

  projectTeamMemberIds: Array<number>;
  @Input() organizationTeamMembers: Array<User>;

  validTitle = true;
  validDescription = true;
  errorMessage = '';
  invalidTitleMessage = "must be at least 10 characters";
  invalidDescriptionMessage = "must be at least 10 characters";

  private queryParamsSubscription: Subscription;
  private pageNumber = 1;

  constructor(private projectService: ProjectService,
              private userService: UserService,
              private router: Router,
              private route: ActivatedRoute,
              public activeModal: NgbActiveModal) {}

  ngOnInit(): void {
    this.queryParamsSubscription = this.route.queryParams.subscribe({
      next: params => {
        const pageNumber = +params['page'];
        if (!isNaN(pageNumber)) {
          this.pageNumber = pageNumber;
        }
      }
    })

    this.formProject = Object.assign({}, this.project);
    this.userService.getUsers().subscribe({
      next: users => {
        this.organizationTeamMembers = users;
        this.projectTeamMemberIds = this.project.teamMembers.map(user => user.id);
      }
    });
  }

  onSubmit() {
    this.formProject.name = this.formProject.name.trim();
    this.formProject.description = this.formProject.description.trim();

    this.formProject.teamMembers = this.projectTeamMemberIds.map(projectTeamMemberId => {
      const user = new User();
      user.id = projectTeamMemberId;
      return user;
    });

    this.projectService.update(this.formProject).subscribe({
      next: next => {
        this.closeModal('Submit click')
      },
      error: (err: HttpErrorResponse) => {
        const simpleResponse = err.error as SimpleResponse;
        this.errorMessage = simpleResponse.message;
      }
    })
  }

  checkIfTitleIsValid(): void {
    if (this.formProject.name) {
      this.formProject.name = this.formProject.name.trim();
      this.validTitle = this.formProject.name.trim().length >= 10;
    } else {
      this.validTitle = false;
    }
  }

  checkIfDescriptionIsValid(): void {
    if (this.formProject.description) {
      this.formProject.description = this.formProject.description.trim();
      this.validDescription = this.formProject.description.length >= 10;
    } else {
      this.validTitle = false;
    }
  }

  closeModal(reason: string): void {
    this.activeModal.close(reason);
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {'page': this.pageNumber}
    });
  }

  ngOnDestroy(): void {
    if (this.queryParamsSubscription != null) {
      this.queryParamsSubscription.unsubscribe();
    }
  }


}
