import {Injectable} from '@angular/core';
import {
  Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import {Observable, of} from 'rxjs';
import {User} from "../model/user";
import {UserService} from "../services/user-service/user.service";

@Injectable({
  providedIn: 'root'
})
export class UsersResolver implements Resolve<Observable<Array<User>>> {

  constructor(private userService: UserService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<User>> {
    return this.userService.getUsers();
  }
}
