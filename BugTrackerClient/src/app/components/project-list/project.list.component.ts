import {Component, OnInit} from '@angular/core';
import {ProjectService} from "../../services/project.service";
import {Project} from "../../model/project";
import {faEllipsisV} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-project-list',
  templateUrl: './project.list.component.html',
  styleUrls: ['./project.list.component.css']
})
export class ProjectListComponent implements OnInit {
  faEllipsisV = faEllipsisV
  projects = new Array<Project>();
  projectsCount = 0;
  page = 1;

  constructor(private projectService: ProjectService) {
  }

  ngOnInit(): void {
    this.projectService.getCount().subscribe({
      next: count => {
        this.projectsCount = count;
      }
    });
    this.loadPageContent(1);
  }


  onPageChange() {
    this.loadPageContent(this.page);
  }

  private loadPageContent(pageNumber: number): void {
    this.projectService.listByPage(pageNumber).subscribe({
      next: projects => {
        this.projects = projects;
      }
    })
  }

  editProject(project: Project) {
    console.log(`editing ...`, project.id);
  }
}
