import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrls: ['./activation.component.css']
})

export class ActivationComponent implements OnInit {

  public successMessage = 'Thank you, your email address has been verified and confirmed!';
  public failMessage = 'Your email address has not been verified';

  confirmationMessage: string;

  constructor(private route: ActivatedRoute,
              private userService: UserService) {
}

  ngOnInit(): void {
    this.sendToken()
  }

  sendToken() {
    const token = this.route.snapshot.paramMap.get('token');
  }

}
