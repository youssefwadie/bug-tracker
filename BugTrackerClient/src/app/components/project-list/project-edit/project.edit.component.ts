import {Component, OnInit} from '@angular/core';
import {Project} from "../../../model/project";
import {ActivatedRoute, Router} from "@angular/router";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {ProjectService} from "../../../services/project-service/project.service";
import {User} from "../../../model/user";
import {UserService} from "../../../services/user-service/user.service";

@Component({
  selector: 'app-project-edit',
  templateUrl: './project.edit.component.html',
  styleUrls: ['./project.edit.component.css']
})
export class ProjectEditComponent implements OnInit {
  project: Project;
  formProject: Project;

  selectedUsersIds: Array<number>;
  users: Array<User>;
  private validTitle = false;
  private validDescription = false;

  constructor(private projectService: ProjectService,
              private userService: UserService,
              private router: Router,
              private route: ActivatedRoute,
              public activeModal: NgbActiveModal) {
  }

  ngOnInit(): void {
    this.formProject = Object.assign({}, this.project);
    this.userService.getUsers().subscribe({
      next: users => {
        this.users = users;
        this.selectedUsersIds = this.project.teamMembers.map(user => user.id);
      }
    })
  }

  onSubmit() {
    this.formProject.name = this.formProject.name.trim();
    this.formProject.description = this.formProject.description.trim();

    console.log(`submitting`, this.formProject);
    console.log(this.selectedUsersIds);

    this.router.navigate([], {
      relativeTo: this.route,
    });

  }

  checkIfNameIsValid(): void {
    if (this.formProject.name) {
      this.formProject.name = this.formProject.name.trim();
      this.validTitle = this.formProject.name.trim().length >= 10;
    }
  }

  checkIfDescriptionIsValid(): void {
    if (this.formProject.description) {
      this.formProject.description = this.formProject.description.trim();
      this.validDescription = this.formProject.description.length >= 10;
    }
  }

}
