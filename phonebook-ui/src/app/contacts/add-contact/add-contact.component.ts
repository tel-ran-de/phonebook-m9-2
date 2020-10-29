import { Component, OnInit } from '@angular/core';
import {ContactsService} from "../../service/contact.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-contact',
  templateUrl: './add-contact.component.html',
  styleUrls: ['./add-contact.component.css']
})
export class AddContactComponent implements OnInit {

  title = 'Add Contact';
  AddContactForm: FormGroup;
  loading: boolean;
  constructor(private fb: FormBuilder,private contactsService: ContactsService, public router: Router) {
    this.createForm();
  }

  createForm() {
    this.AddContactForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      description:['']
    });
  }
  ngOnInit(): void {
  }

  onSubmit() {
    this.loading = true;
    this.contactsService.addContact(this.AddContactForm.value)
      .subscribe((response) => {
          this.loading = false;
          this.router.navigate(['/user/add-phone/'+ response.id]);
        },
        error => {
          this.loading = false;
        });
  }
}
