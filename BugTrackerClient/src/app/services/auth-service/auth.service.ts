import {EventEmitter, Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Role, User, UserLogin} from "../../model/user";
import {combineLatest, map, Observable, of, tap} from "rxjs";
import {environment} from "../../../environments/environment";
import {SimpleResponse} from "../../model/response.body";
import {UserService} from "../user-service/user.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_LOGIN_PATH = 'users/login';
  private readonly API_LOGOUT_PATH = 'users/logout'
  private readonly API_IS_LOGGED_IN_PATH = 'users/logged-in';
  private readonly API_RESEND_VERIFICATION_CODE_PATH = "users/resend";
  private readonly API_USER_ROLE_PATH = "users/role";

  private readonly roleSetEvent = new EventEmitter<string>();

  private isAuthenticated = false;
  private checked = false;
  public readonly authenticationResultEvent = new EventEmitter<boolean>();
  public readonly authenticationMessageEvent = new EventEmitter<string>();

  constructor(private http: HttpClient) {
  }

  authenticate(user: UserLogin): void {
    this.http.post<User>(`${environment.rootUrl}/${this.API_LOGIN_PATH}`, {}, {
      withCredentials: true,
      headers: {
        Authorization:
          'Basic ' + window.btoa(user.email + ':' + user.password),
      },
    }).subscribe({
      next: () => {
        this.setupAuthenticationDetails();
      }, error: (err: HttpErrorResponse) => {
        const responseError = err.error as SimpleResponse;
        if (responseError) {
          this.authenticationMessageEvent.emit(responseError.message);
        } else {
          this.authenticationMessageEvent.emit('invalid email or password');
        }
        this.removeAuthenticationDetails();
      }
    });

  }

  public isAdmin(): Observable<boolean> {
    return combineLatest([this.isLoggedIn(), this.getUserRole().pipe(map(role => role === Role.ROLE_ADMIN))])
      .pipe(map(([loggedIn, isAdmin]) => {
        return loggedIn && isAdmin;
      }));
  }

  logout() {
    return this.http.post<string>(`${environment.rootUrl}/${this.API_LOGOUT_PATH}`, {}, {
      withCredentials: true
    }).subscribe({
      next: () => {
        this.removeAuthenticationDetails();
      }, error: () => {
        this.removeAuthenticationDetails();
      }
    });
  }

  isLoggedIn(): Observable<boolean> {
    if (this.isAuthenticated) {
      return of(true);
    }

    if (!this.checked) {
      return this.http.get<boolean>(`${environment.rootUrl}/${this.API_IS_LOGGED_IN_PATH}`,
        {withCredentials: true})
        .pipe(tap(loggedIn => {
          this.checked = true;
          if (loggedIn) {
            this.setupAuthenticationDetails();
          } else {
            this.removeAuthenticationDetails();
          }
        }));
    } else {
      return of(false);
    }
  }

  resendVerificationToken(email: string): Observable<SimpleResponse> {
    return this.http.post<SimpleResponse>(`${environment.rootUrl}/${this.API_RESEND_VERIFICATION_CODE_PATH}`, {email});
  }

  private getUserRole(): Observable<Role> {
    return this.http.get<Role>(`${environment.rootUrl}/${this.API_USER_ROLE_PATH}`, {withCredentials: true})
      .pipe(map(UserService.roleFromHttp));
  }

  private setupAuthenticationDetails() {
    this.getUserRole().subscribe(userRole => this.roleSetEvent.emit(userRole));
    this.authenticationResultEvent.emit(true);
    this.isAuthenticated = true;
  }


  private removeAuthenticationDetails() {
    this.roleSetEvent.emit('');
    this.authenticationResultEvent.emit(false);
    this.isAuthenticated = false;
  }

}
