import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Ticket} from "../../model/Ticket";
import {TicketService} from "../../services/ticket.service";

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.css']
})
export class TicketsComponent implements OnInit {
  page: number = 1;

  tickets = new Array<Ticket>();

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {
  }

  onPageChange(): void {
    console.log(`page number = ${this.page}`)
  }
}
