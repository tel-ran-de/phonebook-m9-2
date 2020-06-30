import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../service/user.service";
import {ConfirmedValidator} from "./confirmed.validator";

@Component({
  selector: 'app-password-recovery',
  templateUrl: './password-recovery.component.html',
  styleUrls: ['./password-recovery.component.css']
})
export class PasswordRecoveryComponent implements OnInit {
  title = 'Password Recovery';
  PasswordRecoveryForm: FormGroup;
  loading: boolean;
  submitted: boolean = true;

  constructor(private fb: FormBuilder, private router: Router, private userService: UserService, private route: ActivatedRoute) {
    this.createForm();
  }

  createForm() {
    this.PasswordRecoveryForm = this.fb.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirm_password: ['', [Validators.required]]
    }, {
      validators: ConfirmedValidator('password', 'confirm_password')
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    const token = this.route.snapshot.paramMap.get('token');

    this.loading = true;

    this.userService.resetPassword(this.PasswordRecoveryForm.value, token)
      .subscribe(
        data => {
          this.loading = false;
          this.submitted = false;
          //this.router.navigate(['user/login']);
        },
        error => {
          console.log("error connection");
          this.loading = false;
        }
      )

  }
}
