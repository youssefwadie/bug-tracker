import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, map, Observable, of, tap } from 'rxjs';
import { Project } from '../../model/project';
import { environment } from '../../../environments/environment';
import { TicketService } from '../ticket-service/ticket.service';
import { User } from '../../model/user';
import { Ticket } from 'src/app/model/ticket';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private readonly API_LIST_BY_PAGE_PATH = 'projects/page';
  private readonly API_FIND_BY_ID_PATH = 'projects';
  private readonly API_UPDATE_PROJECT: string = 'admin/projects';

  private backingProjects = new Array<Project>();
  private readonly projects$ = new BehaviorSubject<Project[]>(
    new Array<Project>()
  );

  private apiListProjectTicketsByPagePath(
    projectId: number,
    pageNumber: number
  ): string {
    return `projects/${projectId}/tickets/page/${pageNumber}`;
  }

  private apiCountTeamMembersPath(projectId: number): string {
    return `projects/${projectId}/members/count`;
  }

  private apiListTeamMembersByPagePath(
    projectId: number,
    pageNumber: number
  ): string {
    return `projects/${projectId}/members/page/${pageNumber}`;
  }

  constructor(private http: HttpClient) {}

  listProjectsTicketByPage(
    projectId: number,
    pageNumber: number
  ): Observable<HttpResponse<Array<Ticket>>> {
    return this.http
      .get<Array<Ticket>>(
        `${environment.rootUrl}/${this.apiListProjectTicketsByPagePath(
          projectId,
          pageNumber
        )}`,
        { withCredentials: true, observe: 'response' }
      )
      .pipe(
        map((response: HttpResponse<Array<Ticket>>) => {
          const tickets = response.body;
          if (tickets) {
            tickets.map(TicketService.fromHttp);
          }
          return response;
        })
      );
  }

  listByPage(pageId: number): Observable<HttpResponse<Array<Project>>> {
    return this.http
      .get<Array<Project>>(
        `${environment.rootUrl}/${this.API_LIST_BY_PAGE_PATH}/${pageId}`,
        {
          withCredentials: true,
          observe: 'response',
        }
      )
      .pipe(
        tap((response) => {
          const projects = response.body;
          if (projects) {
            this.updateBackingProjects(projects);
          }
        })
      );
  }

  findById(id: number): Observable<Project> {
    const project = this.backingProjects.find((project) => project.id === id);
    if (project) {
      if (project.teamMembers.length !== 0) {
        return of(project);
      }
    }

    return this.http
      .get<Project>(
        `${environment.rootUrl}/${this.API_FIND_BY_ID_PATH}/${id}`,
        { withCredentials: true }
      )
      .pipe(map(ProjectService.fromHttp))
      .pipe(
        tap((project) => {
          this.updateBackingProjects([project]);
        })
      );
  }

  private updateBackingProjects(projects: Project[]) {
    for (const project of projects) {
      const projectIndex = this.backingProjects.findIndex(
        (element) => element.id === project.id
      );
      if (projectIndex !== -1) {
        if (
          this.equals(this.backingProjects[projectIndex], project) &&
          this.backingProjects[projectIndex].teamMembers.length <=
            project.teamMembers.length
        ) {
          this.backingProjects[projectIndex] = project;
        }
      } else {
        this.backingProjects.push(project);
      }
    }
    this.projects$.next(this.backingProjects);
  }

  private equals(project1: Project, project2: Project): boolean {
    return (
      project1.name === project2.name &&
      project1.description === project2.description
    );
  }

  private static fromHttp(project: Project) {
    project.tickets = project.tickets.map(TicketService.fromHttp);
    return project;
  }

  getTeamMembers(
    projectId: number,
    pageNumber: number
  ): Observable<HttpResponse<Array<User>>> {
    return this.http.get<Array<User>>(
      `${environment.rootUrl}/${this.apiListTeamMembersByPagePath(
        projectId,
        pageNumber
      )}`,
      { withCredentials: true, observe: 'response' }
    );
  }

  getTeamMembersCount(projectId: number) {
    return this.http.get<number>(
      `${environment.rootUrl}/${this.apiCountTeamMembersPath(projectId)}`,
      { withCredentials: true }
    );
  }

  update(formProject: Project): Observable<Project> {
    return this.http.put<Project>(
      `${environment.rootUrl}/${this.API_UPDATE_PROJECT}`,
      formProject,
      { withCredentials: true }
    );
  }
}
