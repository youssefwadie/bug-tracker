import {Component, OnInit} from '@angular/core';
import {Ticket} from "../../model/ticket";
import {TicketService} from "../../services/ticket.service";

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket.list.component.html',
  styleUrls: ['./ticket.list.component.css']
})
export class TicketListComponent implements OnInit {
  page: number = 1;
  tickets = new Array<Ticket>();
  ticketsCount = 0;

  constructor(private ticketService: TicketService) {
  }

  ngOnInit(): void {
    this.ticketService.getCount().subscribe(next => {
      this.ticketsCount = next;
    });
    this.ticketService.listByPage(1).subscribe({
      next: tickets => {
        this.tickets = tickets;
      }
    });
  }

  onPageChange(): void {
    this.ticketService.listByPage(this.page).subscribe({
      next: tickets => {
        this.tickets = tickets;
      }
    })
  }

}
