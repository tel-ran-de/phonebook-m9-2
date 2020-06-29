import {Injectable} from '@angular/core'
import {User} from "./interface";
import {HttpClient} from '@angular/common/http'

@Injectable()
export class UserService {

  private readonly forgotPasswordPath = '/api/user/password/recovery/';
  private readonly resetPasswordPath = '/api/user/password/';
  private readonly userPath = '/api/user/';
  private readonly activationPath = '/api/user/activation/';

  constructor(private http: HttpClient) {
  }

  newUserRegistration(user: User) {
    return this.http.post<User>(this.userPath, user);
  }

  sendRequestToConfirmRegistration(token: string) {
    return this.http.get(`${this.activationPath}${token}`)
  }

  forgotPassword(user: User) {
    return this.http.post<User>(this.forgotPasswordPath, user);
  }

  resetPassword(user: User, token: string) {
    return this.http.put<User>(this.resetPasswordPath, {
      password: user.password,
      token: token
    });
  }
}
