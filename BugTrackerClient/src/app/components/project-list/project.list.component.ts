import {Component, OnInit} from '@angular/core';
import {ProjectService} from "../../services/project-service/project.service";
import {Project} from "../../model/project";
import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ProjectEditComponent} from "./project-edit/project.edit.component";
import {PageEvent} from "@angular/material/paginator";
import {AppConstants} from "../../constants/app-constants";

@Component({
  selector: 'app-project-list',
  templateUrl: './project.list.component.html',
  styleUrls: ['./project.list.component.css']
})
export class ProjectListComponent implements OnInit {
  projects = new Array<Project>();
  projectsCount = 0;
  action: string;
  selectedProject: Project;
  isAdmin = false;
  displayedColumns: string[] = ["name", "description", "edit"];

  constructor(private projectService: ProjectService,
              private modalService: NgbModal,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.loadPageContent(1);
    this.processQueryParams();
  }

  private processQueryParams() {
    this.route.queryParams.subscribe((params) => {
      const id = params['id'];
      this.action = params['action'];
      if (id) {
        this.projectService.findById(+id).subscribe((fetchedProject) => {
          this.selectedProject = fetchedProject;
          this.open();
        });
      } else {
        if (this.action === 'add') {
          this.selectedProject = new Project();
          this.open();
        }
      }
    });
  }

  onPageChange(pageEvent: PageEvent) {
    this.loadPageContent(pageEvent.pageIndex + 1);
  }


  loadPageContent(pageNumber: number): void {
    this.projectService.listByPage(pageNumber).subscribe({
      next: response => {
        const projectCount = Number(response.headers.get(AppConstants.TOTAL_COUNT_HEADER_NAME));
        if (!isNaN(projectCount)) this.projectsCount = projectCount;

        const responseBody = response.body;
        if (responseBody != null) this.projects = responseBody;
      }
    })
  }

  editProject(project: Project) {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {'action': 'edit', 'id': project.id},
    });

  }

  addProject(): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {'action': 'add'},
    });
  }

  viewProject(projectId: number) {
    this.router.navigate(["project", projectId]);
  }


  open() {
    const modalRef = this.modalService.open(ProjectEditComponent);
    const component = modalRef.componentInstance as ProjectEditComponent;
    component.project = this.selectedProject;

    modalRef.result.then(
      () => {
      }, (reason) => {
        this.router.navigate([], {
          relativeTo: this.route,
          queryParams: {},
        });
      }
    );
  }

  projectDetails(id: number) {
    this.projectService.findById(id).subscribe(project => {
      this.selectedProject = project;
      console.log(project);
    });
  }

}
