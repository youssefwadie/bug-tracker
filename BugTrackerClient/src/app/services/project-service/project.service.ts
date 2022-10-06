import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Project} from "../../model/project";
import {environment} from "../../../environments/environment";
import {TicketService} from "../ticket-service/ticket.service";
import {User} from "../../model/user";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private readonly API_LIST_BY_PAGE_PATH = "projects/page";
  private readonly API_FIND_BY_ID_PATH = "projects";
  private readonly API_UPDATE_PROJECT: string = "admin/projects";

  private apiCountTeamMembersPath(projectId: number): string {
    return `projects/${projectId}/members/count`;
  }

  private apiListTeamMembersByPagePath(projectId: number, pageNumber: number): string {
    return `projects/${projectId}/members/page/${pageNumber}`;
  }

  constructor(private http: HttpClient) {
  }

  listByPage(pageNumber: number): Observable<HttpResponse<Array<Project>>> {
    return this.http.get<Array<Project>>(`${environment.rootUrl}/${this.API_LIST_BY_PAGE_PATH}/${pageNumber}`,
      {withCredentials: true, observe: 'response'}
    );
  }

  findById(id: number): Observable<Project> {
    return this.http.get<Project>(`${environment.rootUrl}/${this.API_FIND_BY_ID_PATH}/${id}`, {withCredentials: true})
      .pipe(map(ProjectService.fromHttp));
  }

  private static fromHttp(project: Project) {
    project.tickets = project.tickets.map(TicketService.fromHttp);
    return project;
  }

  getTeamMembers(projectId: number, pageNumber: number): Observable<Array<User>> {
    return this.http.get<Array<User>>(`${environment.rootUrl}/${this.apiListTeamMembersByPagePath(projectId, pageNumber)}`,
      {withCredentials: true});
  }

  getTeamMembersCount(projectId: number) {
    return this.http.get<number>(`${environment.rootUrl}/${this.apiCountTeamMembersPath(projectId)}`,
      {withCredentials: true});
  }

  update(formProject: Project): Observable<Project> {
    return this.http.put<Project>(`${environment.rootUrl}/${this.API_UPDATE_PROJECT}`, formProject,
      {withCredentials: true});
  }
}
