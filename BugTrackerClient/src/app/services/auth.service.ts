import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User, UserRole} from "../model/user";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {SimpleResponse} from "../model/response.body";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_LOGIN_PATH = 'users/login';
  private readonly API_LOGOUT_PATH = 'users/logout'
  private readonly API_IS_LOGGED_IN_PATH = 'users/logged-in';
  private readonly API_RESEND_VERIFICATION_CODE_PATH = "users/resend";
  private readonly API_USER_ROLE_PATH = "users/role";

  constructor(private http: HttpClient) {
  }

  login(user: User): Observable<User> {
    return this.http.post<User>(`${environment.rootUrl}/${this.API_LOGIN_PATH}`, {}, {
      withCredentials: true,
      headers: {
        Authorization:
          'Basic ' + window.btoa(user.email + ':' + user.password),
      },
    });
  }

  logout(): Observable<string> {
    return this.http.post<string>(`${environment.rootUrl}/${this.API_LOGOUT_PATH}`, {}, {
      withCredentials: true
    });
  }

  isLoggedIn(): Observable<boolean> {
    return this.http.get<boolean>(`${environment.rootUrl}/${this.API_IS_LOGGED_IN_PATH}`,
      {withCredentials: true});
  }

  resendVerificationToken(email: string): Observable<SimpleResponse> {
    return this.http.post<SimpleResponse>(`${environment.rootUrl}/${this.API_RESEND_VERIFICATION_CODE_PATH}`, {email});
  }
  getUserRole(): Observable<UserRole> {
    return this.http.get<UserRole>(`${environment.rootUrl}/${this.API_USER_ROLE_PATH}`, {withCredentials: true});
  }

}
