import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthenticationService} from "../service/authentication.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  email = '';
  password = '';
  invalidLogin = false;

  @Input() error: string | null;

  constructor(private fb: FormBuilder, private router: Router,
              private loginservice: AuthenticationService) {
    this.createForm();
  }

  createForm() {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20)]]
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    (this.loginservice.authenticate(this.email, this.password).subscribe(
        data => {
          this.router.navigate(['']);
          this.invalidLogin = false;
        },
        error => {
          this.invalidLogin = true;
          this.error = error.status;
          if (this.error  == '401'){
            this.error = "Login or Password incorrect"
          }
        }
      )
    );
  }
}

