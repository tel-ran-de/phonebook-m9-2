import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../service/authentication.service";
import {Contact} from "../model/contact";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  contact: Contact;
  isLoggedIn: boolean;
  viewUserInfo: string;
  id: number;

  constructor(private tokenService: AuthenticationService, private userService: UserService,) {}

  ngOnInit(): void {
    this.userService.getProfile().subscribe(response => {
      this.contact = response;
      this.id = response.id;
      this.isUserLoggedIn()
      this.checkUserInfo()
    });
  }

  isUserLoggedIn(){
    if (this.tokenService.isUserLoggedIn()) {
      this.isLoggedIn = true;
    }
  }

  checkUserInfo() {
    localStorage.setItem('firstName', this.contact.firstName);
    localStorage.setItem('lastName', this.contact.lastName);

    if (localStorage.getItem('firstName') != "") {
      this.viewUserInfo = localStorage.getItem('firstName') + " " + localStorage.getItem('lastName');
    } else {
      this.viewUserInfo = this.tokenService.getEmail()
    }
  }

}
