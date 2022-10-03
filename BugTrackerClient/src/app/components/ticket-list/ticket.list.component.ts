import {Component, Input, OnInit} from '@angular/core';
import {faEllipsisV} from '@fortawesome/free-solid-svg-icons';
import {Ticket} from "../../model/ticket";
import {TicketService} from "../../services/ticket-service/ticket.service";
import {PageEvent} from "@angular/material/paginator";

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
      this.ticketService.getCount().subscribe(next => {
        this.ticketsCount = next;
      });
      this.ticketService.listByPage(1).subscribe({
        next: tickets => {
          this.tickets = tickets;
        }
      });
    }
  }

  onPageChange(pageEvent: PageEvent): void {
    this.ticketService.listByPage(pageEvent.pageIndex + 1).subscribe({
      next: tickets => {
        this.tickets = tickets;
      }
    });
  }

  // TODO: to be implemented
  editTicket(ticket: Ticket) {
    console.log(`editing ... ${ticket.id}`);
  }
}
