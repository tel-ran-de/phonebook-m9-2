import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ContactsService} from "../../service/contact.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-address',
  templateUrl: './add-address.component.html',
  styleUrls: ['./add-address.component.css']
})
export class AddAddressComponent implements OnInit {
  title = 'Add Address';
  AddAddressForm: FormGroup;
  loading: boolean;
  private contactId: number;

  constructor(private fb: FormBuilder, private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute) {
    this.createForm();
  }

  ngOnInit() {
    this.contactId = this.activatedRoute.snapshot.params.contactId;
  }

  createForm() {
    this.AddAddressForm = this.fb.group({
      zip: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
      country: ['', [Validators.required]],
      city: ['', [Validators.required]],
      street: ['', [Validators.required]],
    });
  }

  onSubmit() {
    this.loading = true;
    this.contactsService.addAddress(this.AddAddressForm.value, this.contactId)
      .subscribe((response) => {
          this.loading = false;
          this.router.navigate(['/user/contact/' + this.contactId]);
        },
        error => {
          this.loading = false;
        });
  }
}
