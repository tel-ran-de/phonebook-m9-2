import {Component, OnInit} from '@angular/core';
import {Contact} from "../../model/contact";
import {ContactsService} from "../../service/contact.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-update-phone',
  templateUrl: './update-phone.component.html',
  styleUrls: ['./update-phone.component.css']
})
export class UpdatePhoneComponent implements OnInit {
  title = 'Update Phone';
  UpdatePhoneForm: FormGroup;
  loading: boolean;

  contact: Contact;
  phoneId: number;
  phoneNumber: number;
  country_code: any;


  constructor(private fb: FormBuilder, private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute) {
   // this.contact = new Contact();
    this.createForm();

  }

  ngOnInit() {
    this.phoneId = this.activatedRoute.snapshot.params.phoneId;
    this.contactsService.getPhone(this.phoneId).subscribe(response => {
      this.contact = response;
      this.getCountry_Code();
    });
  }

  getCountry_Code(){
    this.contactsService.getCountry_code().subscribe(response => {
      this.country_code = response;
      this.setDefault();
    });
}
  setDefault() {
    let phone = {
      countryCode: this.contact.countryCode,
      phoneNumber: this.contact.phoneNumber
    };
    this.UpdatePhoneForm.setValue(phone);
  }

  createForm() {
    this.UpdatePhoneForm = this.fb.group({
      countryCode: [''],
      phoneNumber: ['', [Validators.required, Validators.pattern("^[0-9]*$")]]
    });
  }

  onSubmit() {
    this.loading = true;
    this.contactsService.updatePhone(this.UpdatePhoneForm.value, this.contact.contactId, this.phoneId)
      .subscribe((response) => {
        this.loading = false;
        this.router.navigate(['/user/contact/' + this.contact.contactId]);
      }, error => {
        this.loading = false;
      });
  }
}
