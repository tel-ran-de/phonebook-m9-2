import { Component, OnInit } from '@angular/core';
import { Subscription } from "rxjs";
import { ActivatedRoute } from "@angular/router";
import { UserService } from "../service/user.service";

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrls: ['./activation.component.css']
})
export class ActivationComponent implements OnInit {

  private subscription: Subscription;
  RegistrationSuccess = true;

  constructor(private route: ActivatedRoute,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.sendToken()
  }

  sendToken() {
    const token = this.route.snapshot.paramMap.get('token');

    this.subscription = this.userService.sendRequestToConfirmRegistration(token).subscribe(_ => {
      },
      _ => {
        this.RegistrationSuccess = false;
      });
  }

}
