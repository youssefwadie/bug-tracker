import {Component, OnInit} from '@angular/core';
import {Ticket} from "../../model/Ticket";
import {TicketService} from "../../services/ticket.service";
import {ObjectUnsubscribedError, Observable} from "rxjs";

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.css']
})
export class TicketsComponent implements OnInit {
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
