import {Component, OnInit} from '@angular/core';
import {Contact} from "../../model/contact";
import {ContactsService} from "../../service/contact.service";
import {ActivatedRoute, Router} from "@angular/router";
import {CommonService} from "../../service/common.service";

@Component({
  selector: 'app-update-phone',
  templateUrl: './update-phone.component.html',
  styleUrls: ['./update-phone.component.css']
})
export class UpdatePhoneComponent implements OnInit {
  contact: Contact;
  phoneId: number;
  phoneNumber: number;
  country_code: any;
  default_Country_Code = '49';
  selected_Country_Code = '49';

  constructor(private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute, private commonService: CommonService) {
    this.contact = new Contact();
  }

  ngOnInit() {
    this.phoneId = this.activatedRoute.snapshot.params.phoneId;

    this.contactsService.getPhone(this.phoneId).subscribe(response => {

      this.contact = response

      this.contactsService.getCountry_code().subscribe(response => {
        this.country_code = response;
      });

    });

  }

  submitForm() {

    this.contactsService.updatePhone(this.contact, this.phoneId, this.selected_Country_Code).subscribe((response) => {
      this.router.navigate(['/user/contact/' + this.contact.contactId]);
    });

  }


  selectChange() {
    this.selected_Country_Code = this.commonService.getDropDownText(this.default_Country_Code, this.country_code)[0].code;
  }

}
