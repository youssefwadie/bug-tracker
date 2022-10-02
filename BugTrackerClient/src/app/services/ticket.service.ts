import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {EMPTY, map, Observable} from "rxjs";
import {Ticket} from "../model/ticket";
import {environment} from "../../environments/environment";
import {TicketsReport} from "../model/tickets.report";

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private readonly API_NUMBER_OF_TICKETS_PATH = 'tickets/count';
  private readonly API_LIST_BY_PAGE = 'tickets/page';

  constructor(private http: HttpClient) {
  }

  getCount(): Observable<number> {
    return this.http.get<number>(`${environment.rootUrl}/${this.API_NUMBER_OF_TICKETS_PATH}`, {withCredentials: true});
  }

  listByPage(pageNumber: number): Observable<Array<Ticket>> {
    return this.http.get<Array<Ticket>>(`${environment.rootUrl}/${this.API_LIST_BY_PAGE}/${pageNumber}`, {withCredentials: true})
      .pipe(map(tickets => tickets.map(TicketService.fromHttp)));
  }

  public static fromHttp(ticket: Ticket): Ticket {
    if (ticket.createdAt) {
      ticket.createdAt = new Date(ticket.createdAt);
    }
    return ticket;
  }

}
