import {Component, OnInit} from '@angular/core';
import {Contact} from "../../model/contact";

import {ActivatedRoute, Router} from "@angular/router";
import {CommonService} from "../../service/common.service";
import {ContactsService} from "../../service/contact.service";

@Component({
  selector: 'app-add-phone',
  templateUrl: './add-phone.component.html',
  styleUrls: ['./add-phone.component.css']
})
export class AddPhoneComponent implements OnInit {
  contact: Contact
  private contactId: number;
  country_code: any;
  default_Country_Code = '49';
  selected_Country_Code = '49';

  constructor(private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute, private commonService: CommonService) {
    this.contact = new Contact();
  }

  ngOnInit() {
    this.contactId = this.activatedRoute.snapshot.params.contactId;
    this.contactsService.getCountry_code().subscribe(response => {
      this.country_code = response;
      console.log(response);
    });

  }

  submitForm() {

    this.contactsService.addPhone(this.contact, this.contactId, this.selected_Country_Code).subscribe((response) => {
      this.router.navigate(['/user/contact/' + this.contactId]);
    });

  }


  selectChange() {
    this.selected_Country_Code = this.commonService.getDropDownText(this.default_Country_Code, this.country_code)[0].code;
  }

}
