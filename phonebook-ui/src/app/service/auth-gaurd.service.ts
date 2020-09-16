import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {AuthenticationService} from "./authentication.service";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})

export class AuthGaurdService implements CanActivate{

  constructor(private router: Router,
              private authService: AuthenticationService) {}

canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
    if (this.authService.isUserLoggedIn())
      return true;

    this.router.navigate(['user/login']);
    return false;
}
}
