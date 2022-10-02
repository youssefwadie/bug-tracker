import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {map, Observable, tap} from 'rxjs';
import {AuthService} from "../auth-service/auth.service";

@Injectable({
  providedIn: 'root'
})
export class LoginRouteGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    return this.authService.isLoggedIn().pipe(map(value => !value)).pipe(tap(notLoggedIn => {
      // Logged in
      if (!notLoggedIn) {
        this.router.navigate(['']);
      }
    }));
  }

}
