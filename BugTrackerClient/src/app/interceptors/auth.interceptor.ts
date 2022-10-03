import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {Observable} from 'rxjs';


/**
 * Just adds X-Requested-With header to every request to prevent basic auth popup
 */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor() {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const requestWithHeaders = request.clone({
      headers: request.headers.set('X-Requested-With', "XMLHttpRequest")
    });

    return next.handle(requestWithHeaders);
  }
}
