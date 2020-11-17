import {Component, OnInit} from '@angular/core';
import {Contact} from "../../model/contact";
import {ActivatedRoute, Router} from "@angular/router";
import {ContactsService} from "../../service/contact.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-phone',
  templateUrl: './add-phone.component.html',
  styleUrls: ['./add-phone.component.css']
})
export class AddPhoneComponent implements OnInit {
  contact: Contact
  title = 'Add Phone';
  AddContactForm: FormGroup;
  loading: boolean;
  contactId: number;
  country_code: any;


  constructor(private fb: FormBuilder, private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute) {}


  ngOnInit() {
    this.contactId = this.activatedRoute.snapshot.params.contactId;
    this.contactsService.getCountry_code().subscribe(response => {
      this.country_code = response;
      this.createForm(response[0].code)
    });

  }

  createForm(initialCountry) {
    this.AddContactForm = this.fb.group({
      countryCode: [initialCountry],
      phoneNumber: ['', [Validators.required, Validators.pattern("^[0-9]*$"),Validators.minLength(10), Validators.maxLength(15)]]
    });
  }

  onSubmit() {
    this.loading = true;
    this.contactsService.addPhone(this.AddContactForm.value,this.contactId)
      .subscribe((response) => {
        this.loading = false;
      this.router.navigate(['/user/contact/' + this.contactId]);
    },
        error => {
          this.loading = false;
        });
  }
}
