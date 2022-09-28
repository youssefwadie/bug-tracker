import {EventEmitter, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserRegistration} from "../model/UserRegistration";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private readonly API_REGISTRATION_PATH = 'register';

  constructor(private http: HttpClient) {
  }

  register(registration: UserRegistration) {
    return this.http.post<any>(`${environment.rootUrl}/${this.API_REGISTRATION_PATH}`, registration);
  }
}
