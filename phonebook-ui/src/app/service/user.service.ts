import {Injectable} from '@angular/core'
import {User} from "../model/user";
import {HttpClient, HttpErrorResponse} from '@angular/common/http'
import {Subject, throwError} from "rxjs";
import {catchError} from "rxjs/operators";

@Injectable()
export class UserService {

  private readonly forgotPasswordPath = '/api/user/password/recovery/';
  private readonly resetPasswordPath = '/api/user/password/';
  private readonly userPath = '/api/user/';
  private readonly activationPath = '/api/user/activation/';
  public error$: Subject<string> = new Subject<string>()


  constructor(private http: HttpClient) {
  }

  newUserRegistration(user: User) {
    return this.http.post<User>(this.userPath, user)
      .pipe(
        catchError(this.handleError.bind(this))
      )
  }

  sendRequestToConfirmRegistration(token: string) {
    return this.http.get(`${this.activationPath}${token}`)
  }

  forgotPassword(user: User) {
    return this.http.post<User>(this.forgotPasswordPath, user)
      .pipe(
        catchError(this.handleError.bind(this))
      )
  }

  resetPassword(user: User, token: string) {
    return this.http.put<User>(this.resetPasswordPath, {
      password: user.password,
      token: token
    });
  }

  private handleError(error: HttpErrorResponse) {
    const {message} = error.error;

    switch (message) {

      case "Error! This user doesn\'t exist":
        this.error$.next('Error! This user doesn\'t exist')
        break;
      case "Error! User already exists":
        this.error$.next('Error! User already exists')
        break;
    }

    return throwError(error)
  }
}
