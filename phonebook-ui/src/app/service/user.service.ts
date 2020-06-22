import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { User } from "./interface";

@Injectable()
export class UserService {

  private registrationUrl = 'http://localhost:8080/api/user/registration';
  private notificationService: any;

  constructor(private http: HttpClient) {
  }

  newUserRegistration(user: User) {
    return this.http.post<User>(this.registrationUrl, user);
  }

  error(error: HttpErrorResponse) {
    if (error instanceof HttpErrorResponse) {
      if (!navigator.onLine) {
        return this.notificationService.notify('No Internet Connection')
      } else {
        return this.notificationService.notify('${error.status} - ${error.message}');
      }
    }
  }

  sendRequestToConfirmRegistration(token: string) {
    return this.http.get(`http://localhost:8080/api/user/activation?token=${token}`)
  }

}
