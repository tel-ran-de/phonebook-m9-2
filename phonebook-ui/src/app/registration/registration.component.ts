import {Component, OnInit} from '@angular/core';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import {ConfirmedValidator} from "./confirmed.validator";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})

export class RegistrationComponent implements OnInit {

  title = 'Sign up';
  angForm: FormGroup;
  loading: false;
  submitted: boolean;
  error: string;

  constructor(private fb: FormBuilder,
              private router: Router,
              private userService: UserService,
              private route: ActivatedRoute) {

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
    this.submitted = true;

    // @ts-ignore
    this.loading = true;
    this.userService.newUserRegistration(this.angForm.value)
      .subscribe(
        data => {
          this.router.navigate(['user/activateEmail']);
        },
        error => {
          this.userService.error(error);
          this.loading = false;
        }
      )
  }

}
