import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/User";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginPath = 'users/login';

  constructor(private http: HttpClient) {
  }

  login(user: User): Observable<User> {
    return this.http.post<User>(`${environment.rootUrl}/${this.loginPath}`, {}, {
      withCredentials: true,
      headers: {
        Authorization:
          'Basic ' + window.btoa(user.email + ':' + user.password),
      },
    });
  }
}
