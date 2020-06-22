import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { UserService } from "../service/user.service";
import { Subscription } from "rxjs";

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrls: ['./activation.component.css']
})

export class ActivationComponent implements OnInit {

  public successMessage = 'Thank you! Your registration is complete!';
  public failMessage = 'Registration failed';
  private subscription: Subscription;
  confirmationMessage: string;

  constructor(private route: ActivatedRoute,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.sendToken()
  }

  sendToken() {
    const token = this.route.snapshot.paramMap.get('token');

    this.subscription = this.userService.sendRequestToConfirmRegistration(token).subscribe(_ => {
        this.confirmationMessage = this.successMessage;
      },
      _ => {
        this.confirmationMessage = this.failMessage;
      });
  }

}
