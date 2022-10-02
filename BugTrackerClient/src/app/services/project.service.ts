import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Project} from "../model/project";
import {environment} from "../../environments/environment";
import {TicketService} from "./ticket.service";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private readonly API_NUMBER_OF_PROJECT_PATH = 'projects/count';
  private readonly API_LIST_BY_PAGE_PATH = 'projects/page';
  private readonly API_FIND_BY_ID_PATH = 'projects';

  constructor(private http: HttpClient) {
  }


  getCount(): Observable<number> {
    return this.http.get<number>(`${environment.rootUrl}/${this.API_NUMBER_OF_PROJECT_PATH}`, {withCredentials: true});
  }

  listByPage(pageNumber: number): Observable<Array<Project>> {
    return this.http.get<Array<Project>>(`${environment.rootUrl}/${this.API_LIST_BY_PAGE_PATH}/${pageNumber}`, {withCredentials: true});
  }

  findById(id: number): Observable<Project> {
    return this.http.get<Project>(`${environment.rootUrl}/${this.API_FIND_BY_ID_PATH}/${id}`, {withCredentials: true})
      .pipe(map(ProjectService.fromHttp));
  }

  private static fromHttp(project: Project) {
    project.tickets = project.tickets.map(TicketService.fromHttp);
    return project;
  }
}
