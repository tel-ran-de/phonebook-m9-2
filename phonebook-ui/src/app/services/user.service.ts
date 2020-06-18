import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class User {
  email: string;
  password: string;
}

export class UserService {

  private registrationUrl = 'http://localhost:8090/api/auth/registration';

  constructor(private http: HttpClient) {
  }

  newUserRegistration(user: User): Observable<User> {
    return this.http.post<User>(this.registrationUrl, user);
  }

  // error(error: any) {
  //
  // }
}
