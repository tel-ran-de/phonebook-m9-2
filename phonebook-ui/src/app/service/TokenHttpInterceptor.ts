import {Injectable} from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler, HttpErrorResponse} from '@angular/common/http';
import {AuthenticationService} from './authentication.service';
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class TokenHttpInterceptor implements HttpInterceptor {

  constructor(private tokenService: AuthenticationService, private router: Router) {
  }


  intercept(req: HttpRequest<any>, next: HttpHandler) {

    if (this.tokenService.isUserLoggedIn()) {

      req = req.clone({
        setHeaders: {
          "Access-Token": this.tokenService.getToken()
        }
      })
    }

    return next.handle(req)
      .pipe(
        catchError((error: HttpErrorResponse) =>{
          console.log('[Interceptor Error]: ', error)

          if (error.status === 401){
            this.tokenService.logOut()
          }
          return throwError(error)
        })
      )
  }
}
