import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from "@angular/router";
import { ConfirmedValidator } from "./confirmed.validator";
import { UserService } from "../services/user.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})

export class RegistrationComponent implements OnInit {

  title = 'Sign up';
  angForm: FormGroup;
  loading: boolean;
  error: string;

  constructor(private fb: FormBuilder, private router: Router, private userService: UserService) {
    this.createForm();
  }

  createForm() {
    this.angForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirm_password: ['', [Validators.required]]
    }, {
      validators: ConfirmedValidator('password', 'confirm_password')
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.router.navigate(['user/activatemail']);
    // stop here if form is invalid
    if (this.angForm.invalid) {
      return;
  }

    this.userService.newUserRegistration(this.angForm.value)
      .subscribe(_ => this.router.navigate(['../activate-email'], {relativeTo: this.route}),
        error => {
          this.userService.error(error);
          this.loading = false;
        });
  }
}
