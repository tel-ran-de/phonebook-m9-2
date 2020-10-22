import { Component, OnInit } from '@angular/core';
import {ContactsService} from "../../service/contact.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Contact} from "../../model/contact";

@Component({
  selector: 'app-update-email',
  templateUrl: './update-email.component.html',
  styleUrls: ['./update-email.component.css']
})
export class UpdateEmailComponent implements OnInit {
  contact: Contact
  emailId: number;
  constructor(private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute) {
    this.contact = new Contact();
  }

  ngOnInit() {
    this.emailId = this.activatedRoute.snapshot.params.emailId;

    this.contactsService.getEmail(this.emailId).subscribe(response => {
      this.contact = response
    });

  }

  submitForm() {

    this.contactsService.updateEmail(this.contact, this.emailId).subscribe((response) => {
      this.router.navigate(['/user/contact/' + this.contact.contactId]);
    });

  }

}
