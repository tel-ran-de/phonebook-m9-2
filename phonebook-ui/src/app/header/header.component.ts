import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../service/authentication.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean;
   email: string;

  constructor(private tokenService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.email = this.tokenService.getEmail()
    if (this.tokenService.isUserLoggedIn()) {
      this.isLoggedIn = true;
    }
  }

}
