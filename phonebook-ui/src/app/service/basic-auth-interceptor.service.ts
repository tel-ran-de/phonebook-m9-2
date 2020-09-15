import {Injectable} from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler} from '@angular/common/http';
import {AuthenticationService} from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class BasicAuthHttpInterceptorService implements HttpInterceptor {

  constructor() {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {

    if (localStorage.getItem('email') && localStorage.getItem('AccessToken')) {

      req = req.clone({
        setHeaders: {
          AccessToken: localStorage.getItem('AccessToken')
        }
      })
    }

    return next.handle(req);

  }
}
