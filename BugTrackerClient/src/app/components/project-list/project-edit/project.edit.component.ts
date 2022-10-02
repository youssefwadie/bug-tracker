import {Component, OnInit} from '@angular/core';
import {Project} from "../../../model/project";
import {Router} from "@angular/router";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {ProjectService} from "../../../services/project.service";

@Component({
  selector: 'app-project-edit',
  templateUrl: './project.edit.component.html',
  styleUrls: ['./project.edit.component.css']
})
export class ProjectEditComponent implements OnInit {
  project: Project;
  formProject: Project;
  private validTitle: boolean = false;
  private validDescription: boolean = false;

  constructor(private projectService: ProjectService, private router: Router, public activeModal: NgbActiveModal) {
  }

  ngOnInit(): void {
    this.formProject = Object.assign({}, this.project);
  }

  onSubmit() {
    this.formProject.name = this.formProject.name.trim();
    this.formProject.description = this.formProject.description.trim();

    console.log(`submitting`, this.formProject);
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
