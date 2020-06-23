import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { UserService } from "../service/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  title = 'login';
  loginForm: FormGroup;
  loading: false;
  submitted: boolean;
  error: string;

  constructor(private fb: FormBuilder, private router: Router, private userService: UserService) {
    this.createForm();
  }

  createForm() {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });

  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.submitted = true;

    // @ts-ignore
    this.loading = true;
    this.userService.loginUser(this.loginForm.value)
      .subscribe(
        data => {
          this.router.navigate(['user/page']);
        })
  }

}
