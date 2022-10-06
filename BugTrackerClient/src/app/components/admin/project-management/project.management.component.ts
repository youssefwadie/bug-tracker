import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Project} from "../../../model/project";
import {ProjectService} from "../../../services/project-service/project.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AuthService} from "../../../services/auth-service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {PageEvent} from "@angular/material/paginator";
import {ProjectEditComponent} from "./project-edit/project.edit.component";
import {AppConstants} from "../../../constants/app-constants";
import {User} from "../../../model/user";

@Component({
  selector: 'app-project-management',
  templateUrl: './project.management.component.html',
  styleUrls: ['./project.management.component.css']
})
export class ProjectManagementComponent implements OnInit {

  projects = new Array<Project>();
  projectsCount = 0;
  action: string;
  selectedProject: Project;
  displayedColumns: string[] = ["name", "description", "edit"];
  pageSize = 5;
  pageNumber = 1;


  private queryParamsSubscription: Subscription;
  organizationTeamMembers: Array<User>;

  constructor(private projectService: ProjectService,
              private modalService: NgbModal,
              private authService: AuthService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.loadPageContent(this.pageNumber);
    this.processQueryParams();
  }

  private processQueryParams() {
    this.queryParamsSubscription = this.route.queryParams.subscribe((params) => {
      const id = params['id'];
      const page = +params['page'];
      this.action = params['action'];
      if (id != null) {
        this.projectService.findById(+id).subscribe((fetchedProject) => {
          this.selectedProject = fetchedProject;
          this.open();
        });
      } else if (id == null && this.action === "add") {
        this.selectedProject = new Project();
        this.open();
      } else if (!isNaN(page)) {
        this.loadPageContent(page);
      }
    });
  }

  onPageChange(pageEvent: PageEvent) {
    this.pageNumber = pageEvent.pageIndex + 1;
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {"page": this.pageNumber},
    });
  }


  loadPageContent(pageNumber: number): void {
    this.projectService.listByPage(pageNumber).subscribe({
      next: response => {
        const projectCount = Number(response.headers.get(AppConstants.TOTAL_COUNT_HEADER_NAME));
        if (!isNaN(projectCount)) {
          this.projectsCount = projectCount;
          const maximumPageSize = Math.ceil(this.projectsCount / this.pageSize);
          if (pageNumber > maximumPageSize) {
            this.pageNumber = maximumPageSize + 1;
          } else {
            this.pageNumber = pageNumber;
          }
        }

        const responseBody = response.body;
        if (responseBody != null) this.projects = responseBody;
      }
    })
  }

  editProject(project: Project) {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {"action": "edit", "id": project.id, "page": this.pageNumber},
    });

  }

  addProject(): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {"action": "add", "page": this.pageNumber},
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
          queryParams: {'page': this.pageNumber},
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

  ngOnDestroy(): void {
    if (this.queryParamsSubscription != null) {
      this.queryParamsSubscription.unsubscribe();
    }
  }

}
