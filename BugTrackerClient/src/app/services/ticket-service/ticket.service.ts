import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Ticket, TicketPriority, TicketStatus, TicketType} from "../../model/ticket";
import {environment} from "../../../environments/environment";

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

  listByPage(pageNumber: number): Observable<HttpResponse<Array<Ticket>>> {
    return this.http.get<Array<Ticket>>(`${environment.rootUrl}/${this.API_LIST_BY_PAGE}/${pageNumber}`, {
      withCredentials: true,
      observe: "response"
    }).pipe(map(tickets => {
      if (tickets.body) {
        tickets.body.map(TicketService.fromHttp)
      }
      return tickets;
    }));
  }

  public static fromHttp(ticket: Ticket): Ticket {
    ticket.type = TicketType[ticket.type as string as keyof typeof TicketType];
    ticket.priority = TicketPriority[ticket.priority as string as keyof typeof TicketPriority];
    ticket.status = TicketStatus[ticket.status as string as keyof typeof TicketStatus];

    if (ticket.createdAt) {
      ticket.createdAt = new Date(ticket.createdAt);
    }
    return ticket;
  }

  public static toHttp(ticket: Ticket): Ticket {
    ticket.type = TicketService.ticketTypeToHttp(ticket.type);
    ticket.status = TicketService.ticketStatusToHttp(ticket.status);
    ticket.priority = TicketService.ticketPriorityToHttp(ticket.priority);
    return ticket;
  }

  private static ticketStatusToHttp(status: TicketStatus): TicketStatus {
    const index = Object.values(TicketStatus).indexOf(status);
    if (index !== -1) {
      return Object.keys(TicketStatus)[index] as TicketStatus;
    }
    return status;
  }


  private static ticketPriorityToHttp(priority: TicketPriority): TicketPriority {
    const index = Object.values(TicketPriority).indexOf(priority);
    if (index !== -1) {
      return Object.keys(TicketPriority)[index] as TicketPriority;
    }
    return priority;
  }

  private static ticketTypeToHttp(type: TicketType): TicketType {
    const index = Object.values(TicketType).indexOf(type);
    if (index !== -1) {
      return Object.keys(TicketType)[index] as TicketType;
    }
    return type;
  }

}
