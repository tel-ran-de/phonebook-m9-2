import {Component, OnInit} from '@angular/core';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import {ConfirmedValidator} from "./confirmed.validator";
import {UserService} from "../service/user.service";
import {HttpErrorResponse} from "@angular/common/http";
import {throwError} from "rxjs";

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

  constructor(private fb: FormBuilder,
              private router: Router,
              public userService: UserService) {

    this.createForm();
  }

  createForm() {
    this.angForm = this.fb.group({
      email: ['', [Validators.required, Validators.pattern("^[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,10}$")]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20)]],
      confirm_password: ['', [Validators.required]]
    }, {
      validators: ConfirmedValidator('password', 'confirm_password')
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.loading = true;
    this.userService.newUserRegistration(this.angForm.value)
      .subscribe(
        data => {
          this.router.navigate(['user/activate-email']);
        },
        error => {
          this.loading = false;
        }
      )
  }

}
