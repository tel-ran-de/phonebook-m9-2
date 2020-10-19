import { Component, OnInit } from '@angular/core';
import {Contact} from "../../model/contact";
import {ContactsService} from "../../service/contact.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-add-email',
  templateUrl: './add-email.component.html',
  styleUrls: ['./add-email.component.css']
})
export class AddEmailComponent implements OnInit {
  contact: Contact
  private contactId: number;

  constructor(private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute) {
    this.contact = new Contact();
  }

  ngOnInit(){
    this.contactId = this.activatedRoute.snapshot.params.contactId;
  }

  submitForm() {
    this.contactsService.addEmail(this.contact, this.contactId).subscribe((response) => {
      this.router.navigate(['/user/contact/' + this.contactId]);
    });

  }

}
