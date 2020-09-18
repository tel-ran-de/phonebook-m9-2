import {Injectable} from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler} from '@angular/common/http';
import {AuthenticationService} from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class TokenHttpInterceptor implements HttpInterceptor {

  constructor(private tokenService: AuthenticationService) {
  }


  intercept(req: HttpRequest<any>, next: HttpHandler) {

    if (this.tokenService.isUserLoggedIn()) {

      req = req.clone({
        setHeaders: {
          "Access-Token": this.tokenService.getToken()
        }
      })
    }

    return next.handle(req);

  }
}
