import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from "@angular/router";
import { ConfirmedValidator } from "./confirmed.validator";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})

export class RegistrationComponent implements OnInit {

  title = 'Sign up';
  angForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router,) {
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
  }

}
