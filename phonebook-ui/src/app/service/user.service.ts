import {Injectable} from '@angular/core'
import {User} from "./interface";
import {HttpClient} from '@angular/common/http'

@Injectable()
export class UserService {

  constructor(private http: HttpClient) {
  }

  newUserRegistration(user: User) {
    return this.http.post<User>('/api/user/registration', user);
  }

  sendRequestToConfirmRegistration(token: string) {
    return this.http.get(`/api/user/confirm?token=${token}`)
  }

}
