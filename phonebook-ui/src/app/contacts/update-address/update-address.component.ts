import { Component, OnInit } from '@angular/core';
import {Contact} from "../../model/contact";
import {ContactsService} from "../../service/contact.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-update-address',
  templateUrl: './update-address.component.html',
  styleUrls: ['./update-address.component.css']
})
export class UpdateAddressComponent implements OnInit {
  contact: Contact
  addressId: number;
  constructor(private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute) {
    this.contact = new Contact();
  }

  ngOnInit() {
    this.addressId = this.activatedRoute.snapshot.params.addressId;

    this.contactsService.getAddress(this.addressId).subscribe(response => {
      this.contact = response
    });

  }

  submitForm() {

    this.contactsService.updateAddress(this.contact, this.addressId).subscribe((response) => {
      this.router.navigate(['/user/contact/' + this.contact.contactId]);
    });

  }

}
