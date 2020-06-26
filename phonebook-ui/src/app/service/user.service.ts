import {Injectable} from '@angular/core'
import {User} from "./interface";
import {HttpClient} from '@angular/common/http'

@Injectable()
export class UserService {

  private forgotPasswordUrl = 'http://localhost:8080/api/user/password/recovery';
  private resetPasswordUrl = 'http://localhost:8080/api/user/password/';

  constructor(private http: HttpClient) {
  }

  forgotPassword(user: User) {
    return this.http.post<User>(this.forgotPasswordUrl, user);
  }

  resetPassword(user: User, token: string) {
    return this.http.put<User>(this.resetPasswordUrl, {
      password: user.password,
      token: token
    });
  }
}
