import {Injectable} from '@angular/core'
import {User} from "./interface";
import {HttpClient} from '@angular/common/http'

@Injectable()
export class UserService {

  private registrationUrl = 'http://localhost:8080/api/user/registration';
  private loginUrl = 'http://localhost:8080/api/user/login';

  constructor(private http: HttpClient) {
  }

  newUserRegistration(user: User) {
    return this.http.post<User>(this.registrationUrl, user);
  }

  sendRequestToConfirmRegistration(token: string) {
    return this.http.get(`http://localhost:8080/api/user/activation?token=${token}`)
  }

  loginUser(user: User) {
    return this.http.post<User>(this.loginUrl, user);
  }

}
