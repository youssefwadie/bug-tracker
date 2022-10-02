import {Injectable} from '@angular/core';
import {User} from "../../model/user";
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly API_LIST_USERS_PATH = "admin/users";

  private backingUsers = new Array<User>();
  private readonly users$: BehaviorSubject<User[]>;

  constructor(private http: HttpClient) {
    this.users$ = new BehaviorSubject<User[]>(new Array<User>());
    this.init();
  }

  private init(): void {
    this.http.get<Array<User>>(`${environment.rootUrl}/${this.API_LIST_USERS_PATH}`, {withCredentials: true})
      .subscribe(users => {
        this.backingUsers = users;
        this.users$.next(this.backingUsers);
      });
  }

  public getUsers(): Observable<Array<User>> {
    return this.users$.asObservable();
  }
}
