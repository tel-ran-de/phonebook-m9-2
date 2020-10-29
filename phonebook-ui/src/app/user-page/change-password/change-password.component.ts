import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ConfirmedValidator} from "../../password-recovery/confirmed.validator";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  title = 'Change Password';
  PasswordUpdateForm: FormGroup;
  loading: boolean;
  submitted: boolean = true;

  constructor(private fb: FormBuilder, private userService: UserService, public router: Router) {
    this.createForm();
  }

  createForm() {
    this.PasswordUpdateForm = this.fb.group({
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20)]],
      confirm_password: ['', [Validators.required]]
    }, {
      validators: ConfirmedValidator('password', 'confirm_password')
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    this.loading = true;
    this.userService.changePassword(this.PasswordUpdateForm.value)
      .subscribe((response) => {
          this.loading = false;
          this.submitted = false;
        },
        error => {
          this.loading = false;
        });
  }
}
