import { Component, OnInit } from '@angular/core';
import {Contact} from "../../model/contact";
import {ContactsService} from "../../service/contact.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-update-contact',
  templateUrl: './update-contact.component.html',
  styleUrls: ['./update-contact.component.css']
})
export class UpdateContactComponent implements OnInit {

  title = 'Update Contact';
  UpdateContactForm: FormGroup;
  loading: boolean;
  contact: Contact
  contactId: number;

  constructor(private fb: FormBuilder, private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute) {
    this.contact = new Contact();
    this.createForm();
  }

  ngOnInit() {
    this.contactId = this.activatedRoute.snapshot.params.contactId;
    this.contactsService.getContactById(this.contactId).subscribe(response => {
      this.contact = response
      this.setDefault();
    });

  }

  setDefault() {

    let contact = {
      firstName: this.contact.firstName,
      lastName: this.contact.lastName,
      description: this.contact.description
    };
    this.UpdateContactForm.setValue(contact);
  }


  createForm() {
    this.UpdateContactForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      description:['']
    });

  }


  onSubmit() {

    this.loading = true;
    this.contactsService.updateContact(this.UpdateContactForm.value, this.contactId)
      .subscribe((response) => {
        this.loading = false;
        this.router.navigate(['/user/contact/' + this.contact.id]);
    },
        error => {
        this.loading = false;
        });

  }

}
