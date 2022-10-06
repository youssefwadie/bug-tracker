import {Component, Input, OnInit} from '@angular/core';
import {faEllipsisV} from '@fortawesome/free-solid-svg-icons';
import {Ticket} from "../../model/ticket";
import {TicketService} from "../../services/ticket-service/ticket.service";
import {PageEvent} from "@angular/material/paginator";
import {AppConstants} from "../../constants/app-constants";

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket.list.component.html',
  styleUrls: ['./ticket.list.component.css']
})
export class TicketListComponent implements OnInit {
  @Input() tickets = new Array<Ticket>();
  @Input() ticketsCount = 0;
  @Input() dataSet = false;

  displayedColumns: string[] = ['title', 'project', 'developer', 'type', "priority", "status", "created"];
  faEllipsisV = faEllipsisV;


  constructor(private ticketService: TicketService) {
  }

  ngOnInit(): void {
    if (!this.dataSet) {
      this.loadPage(1);
    }
  }

  onPageChange(pageEvent: PageEvent): void {
    this.loadPage(pageEvent.pageIndex + 1);
  }

  loadPage(pageNumber: number): void {
    this.ticketService.listByPage(pageNumber).subscribe({
      next: response => {
        const ticketsCount = Number(response.headers.get(AppConstants.TOTAL_COUNT_HEADER_NAME));
        if (!isNaN(ticketsCount)) {
          this.ticketsCount = ticketsCount;
        }
        const body = response.body;
        if (body) {
          this.tickets = body;
        }
      }
    });
  }

  // TODO: to be implemented
  editTicket(ticket: Ticket) {
    console.log(`editing ... ${ticket.id}`);
  }
}
