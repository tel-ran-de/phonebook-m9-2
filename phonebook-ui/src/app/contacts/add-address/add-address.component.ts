import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ContactsService} from "../../service/contact.service";
import {Contact} from "../../model/contact";

@Component({
  selector: 'app-add-address',
  templateUrl: './add-address.component.html',
  styleUrls: ['./add-address.component.css']
})
export class AddAddressComponent implements OnInit {
  contact: Contact
  private contactId: number;

  constructor(private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute) {
    this.contact = new Contact();
  }

  ngOnInit(){
    this.contactId = this.activatedRoute.snapshot.params.contactId;
  }

  submitForm() {
    this.contactsService.addAddress(this.contact, this.contactId).subscribe((response) => {
      this.router.navigate(['/']);
    });

  }

}
