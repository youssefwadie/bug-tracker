import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Ticket} from "../model/Ticket";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private readonly API_LIST_ALL_PATH = 'tickets';

  constructor(private http: HttpClient) {

  }

  findAll(): Observable<Array<Ticket>> {
    return this.http.get<Array<Ticket>>(`${environment.rootUrl}/${this.API_LIST_ALL_PATH}`)
  }
}
