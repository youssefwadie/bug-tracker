import {Injectable} from '@angular/core';
import {Role, User} from "../../model/user";
import {BehaviorSubject, map, Observable, of} from "rxjs";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly API_LIST_USERS_PATH = "admin/users";
  private readonly API_FIND_BY_ID_PATH = "admin/users";
  private readonly API_UPDATE_USER_PATH = "admin/users";
  private readonly API_LIST_BY_PAGE_PATH = "admin/users/page";

  private readonly backingUsers: Array<User>;
  private readonly users$: BehaviorSubject<User[]>;

  public static readonly USERS_PER_PAGE = 5;

  constructor(private http: HttpClient) {
    this.backingUsers = new Array<User>();
    this.users$ = new BehaviorSubject<User[]>(this.backingUsers);
    this.initService();
  }

  private initService(): void {
    this.http.get<Array<User>>(`${environment.rootUrl}/${this.API_LIST_USERS_PATH}`, {withCredentials: true})
      .subscribe(users => {
        users.map(UserService.userFromHttp);
        this.updateBackingArray(users);
      });
  }

  public getUsers(): Observable<Array<User>> {
    return this.users$;
  }

  findById(id: number): Observable<User> {
    const index = this.users$.getValue().findIndex((user: User) => user.id === id);
    if (index !== -1) {
      return of(this.users$.getValue()[index]);
    }
    return this.http.get<User>(`${environment.rootUrl}/${this.API_FIND_BY_ID_PATH}/${id}`, {withCredentials: true})
      .pipe(map(UserService.userFromHttp));
  }

  listByPage(pageNumber: number): Observable<HttpResponse<Array<User>>> {
    return this.http.get<Array<User>>(`${environment.rootUrl}/${this.API_LIST_BY_PAGE_PATH}/${pageNumber}`, {
      withCredentials: true,
      observe: "response"
    }).pipe(map(response => {
      const users = response.body as Array<User>;
      if (users) {
        users.map(UserService.userFromHttp);
        this.updateBackingArray(users);
      }
      return response;
    }));

  }


  update(user: User): Observable<User> {
    return this.http.put<User>(`${environment.rootUrl}/${this.API_UPDATE_USER_PATH}`, UserService.userToHttp(user), {
      withCredentials: true
    }).pipe(map(updatedUser => {
      updatedUser = UserService.userFromHttp(updatedUser);
      this.updateBackingArray([updatedUser]);
      return updatedUser;
    }));
  }

  private updateBackingArray(users: Array<User>) {
    users.forEach(user => {
      const index = this.findUserIndexInTheBackingArray(user.id);
      if (index === -1) {
        this.backingUsers.push(user);
      } else {
        this.backingUsers[index] = user;
      }
    });

    this.users$.next(this.backingUsers);
  }

  private findUserIndexInTheBackingArray(userId: number): number {
    if (userId == null) {
      return -1;
    }
    return this.backingUsers.findIndex(user => user.id === userId);
  }

  static userFromHttp(user: User): User {
    user.role = UserService.roleFromHttp(user.role);
    return user;
  }

  static userToHttp(user: User): User {
    user.role = this.roleToHttp(user.role);
    return user;
  }

  static roleFromHttp(role: Role): Role {
    return Role[role as string as keyof typeof Role];
  }

  private static roleToHttp(role: Role): Role {
    const index = Object.values(Role).indexOf(role);
    if (index !== -1) {
      return Object.keys(Role)[index] as Role;
    }
    return role;
  }

  getAllRoles(): Observable<Array<Role>> {
    return of([]);
  }

  checkIfEmailIsUnique(): Observable<boolean> {
    return of(false);
  }
}
