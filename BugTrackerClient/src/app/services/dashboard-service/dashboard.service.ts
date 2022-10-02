import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TicketsReport} from "../../model/tickets.report";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private readonly API_REPORT_PATH = "dashboard/report";

  constructor(private http: HttpClient) {
  }

  getReport(): Observable<TicketsReport> {
    return this.http.get<TicketsReport>(`${environment.rootUrl}/${this.API_REPORT_PATH}`, {withCredentials: true});
  }

}
