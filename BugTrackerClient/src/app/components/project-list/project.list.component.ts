import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProjectService} from "../../services/project-service/project.service";
import {Project} from "../../model/project";
import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ProjectEditComponent} from "../admin/project-management/project-edit/project.edit.component";
import {PageEvent} from "@angular/material/paginator";
import {AppConstants} from "../../constants/app-constants";
import {Subscription} from "rxjs";
import {AuthService} from "../../services/auth-service/auth.service";

@Component({
  selector: 'app-project-list',
  templateUrl: './project.list.component.html',
  styleUrls: ['./project.list.component.css']
})
export class ProjectListComponent implements OnInit, OnDestroy {
  projects = new Array<Project>();
  projectsCount = 0;
  action: string;
  selectedProject: Project;
  displayedColumns: string[] = ["name", "description"];
  pageSize = 5;
  pageNumber = 1;


  private queryParamsSubscription: Subscription;

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
      const page = +params['page'];
      if (!isNaN(page)) {
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


  ngOnDestroy(): void {
    if (this.queryParamsSubscription != null) {
      this.queryParamsSubscription.unsubscribe();
    }
  }

}
