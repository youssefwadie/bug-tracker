import {Component, Input, OnInit} from '@angular/core';
import {faEllipsisV} from '@fortawesome/free-solid-svg-icons';
import {Ticket} from "../../model/ticket";
import {TicketService} from "../../services/ticket-service/ticket.service";
import {PageEvent} from "@angular/material/paginator";
import {AppConstants} from "../../constants/app-constants";
import {ProjectService} from "../../services/project-service/project.service";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket.list.component.html',
  styleUrls: ['./ticket.list.component.css']
})
export class TicketListComponent implements OnInit {
  tickets = new Array<Ticket>();
  ticketsCount = 0;

  @Input() title = "Tickets";
  @Input() projectId: number;
  @Input() pageSize = 10;

  displayedColumns: string[] = ['title', 'project', 'developer', 'type', "priority", "status", "created"];
  faEllipsisV = faEllipsisV;


  constructor(private ticketService: TicketService, private projectService: ProjectService) {
  }

  ngOnInit(): void {
    this.loadPage(1);
  }

  onPageChange(pageEvent: PageEvent): void {
    this.loadPage(pageEvent.pageIndex + 1);
  }

  loadPage(pageNumber: number): void {
    if (this.projectId && !isNaN(this.projectId)) {
      this.projectService.listProjectsTicketByPage(this.projectId, pageNumber).subscribe({
        next: response => {
          this.processResponse(response);
        }
      });
    } else {
      this.ticketService.listByPage(pageNumber).subscribe({
        next: response => {
          this.processResponse(response);
        }
      });
    }
  }

  processResponse(response: HttpResponse<Ticket[]>): void {
    const ticketsCount = Number(response.headers.get(AppConstants.TOTAL_COUNT_HEADER_NAME));
    if (!isNaN(ticketsCount)) {
      this.ticketsCount = ticketsCount;
    }
    const body = response.body;
    if (body) {
      this.tickets = body;
    }
  }

  // TODO: to be implemented
  editTicket(ticket: Ticket) {
    console.log(`editing ... ${ticket.id}`);
  }
}
