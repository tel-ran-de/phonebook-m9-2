import {Component, OnInit} from '@angular/core';
import {Contact} from "../../model/contact";
import {ContactsService} from "../../service/contact.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-update-address',
  templateUrl: './update-address.component.html',
  styleUrls: ['./update-address.component.css']
})
export class UpdateAddressComponent implements OnInit {

  title = 'Update Address';
  UpdateAddressForm: FormGroup;
  loading: boolean;
  addressId: number;
  contact: Contact;

  constructor(private fb: FormBuilder, private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute) {
    this.contact = new Contact();
    this.createForm();
  }

  ngOnInit() {
    this.addressId = this.activatedRoute.snapshot.params.addressId;
    this.contactsService.getAddress(this.addressId).subscribe(response => {
      this.contact = response;
      this.setDefault();
    });
  }

  setDefault() {

    let address = {
      zip: this.contact.zip,
      country: this.contact.country,
      city: this.contact.city,
      street: this.contact.street
    };
    this.UpdateAddressForm.setValue(address);
  }

  createForm() {
    this.UpdateAddressForm = this.fb.group({
      zip: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
      country: ['', [Validators.required]],
      city: ['', [Validators.required]],
      street: ['', [Validators.required]],
    });

  }

  onSubmit() {
    this.loading = true;
    this.contactsService.updateAddress(this.UpdateAddressForm.value, this.contact.contactId, this.addressId)
      .subscribe((response) => {
          this.loading = false;
          this.router.navigate(['/user/contact/' + this.contact.contactId]);
        },
        error => {
          this.loading = false;
        });
  }
}
