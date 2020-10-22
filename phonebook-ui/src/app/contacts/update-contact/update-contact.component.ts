import { Component, OnInit } from '@angular/core';
import {Contact} from "../../model/contact";
import {ContactsService} from "../../service/contact.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-update-contact',
  templateUrl: './update-contact.component.html',
  styleUrls: ['./update-contact.component.css']
})
export class UpdateContactComponent implements OnInit {
  contact: Contact
  contactId: number;
  constructor(private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute) {
    this.contact = new Contact();
  }

  ngOnInit() {
    this.contactId = this.activatedRoute.snapshot.params.contactId;

    this.contactsService.getContactById(this.contactId).subscribe(response => {
      this.contact = response
    });

  }

  submitForm() {

    this.contactsService.updateContact(this.contact, this.contactId).subscribe((response) => {
      this.router.navigate(['/user/contact/' + this.contact.id]);
    });

  }

}
