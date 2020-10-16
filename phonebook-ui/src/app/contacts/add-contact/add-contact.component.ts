import { Component, OnInit } from '@angular/core';
import {Contact} from "../../model/contact";
import {ContactsService} from "../../service/contact.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-contact',
  templateUrl: './add-contact.component.html',
  styleUrls: ['./add-contact.component.css']
})
export class AddContactComponent implements OnInit {

  contact: Contact
  constructor(private contactsService: ContactsService, public router: Router) {
    this.contact = new Contact();
  }

  ngOnInit(): void {
  }

  submitForm() {
    this.contactsService.addContact(this.contact).subscribe((response) => {
      console.log(response)
      this.router.navigate(['/user/addphone/'+ response.id]);
    });

  }
}
