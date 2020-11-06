import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient) {
  }

  authenticate(email, password) {
    return this.httpClient
      .post<any>('/api/user/login', {email, password}, {observe: 'response'})
      .pipe(
        map(userData => {
          let token = userData.headers.get('access-token');
          if (token != null) {
            localStorage.setItem('email', email);
            localStorage.setItem('access-token', token);
            return userData;
          }
        })
      );
  }

  isUserLoggedIn() {
    let user = localStorage.getItem('email');
    return !(user === null);
  }

  logOut() {
    localStorage.removeItem('email');
    localStorage.removeItem('access-token');
  }


  getToken() {
    return localStorage.getItem('access-token')
  }
  getEmail(){
    return localStorage.getItem('email')
  }
}
