import { Component, OnInit } from '@angular/core';
import {Contact} from "../../model/contact";
import {ContactsService} from "../../service/contact.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {AuthenticationService} from "../../service/authentication.service";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  contact: Contact
  addressId: number;
  password: string;
  constructor(private userService: UserService, public router: Router, private tokenService: AuthenticationService) {
    this.contact = new Contact();
  }

  ngOnInit() {}

  submitForm() {

    this.userService.changePassword(this.password).subscribe((response) => {
      this.tokenService.logOut();
      this.router.navigate(['/']);
    });

  }

}
