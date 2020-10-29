import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Contact} from "../../model/contact";
import {ContactsService} from "../../service/contact.service";


@Component({
  selector: 'app-student-edit',
  templateUrl: './contact-details.component.html',
  styleUrls: ['./contact-details.component.css']
})
export class ContactDetailsComponent implements OnInit {

  id: number;
  contact: Contact;
  email: string;

  constructor(
    public activatedRoute: ActivatedRoute,
    public router: Router,
    public contactsService: ContactsService
  ) {
    this.contact = new Contact();
  }

  ngOnInit() {
    this.id = this.activatedRoute.snapshot.params.id;
    this.contactsService.getContactById(this.id).subscribe(response => {
      this.contact = response;
    });
  }

  updatePhone(id: number) {
    this.router.navigate(['/user/update-phone/'+ id]);
  }
  updateEmail(id: number) {
    this.router.navigate(['/user/update-email/'+ id]);
  }
  updateAddress(id: number) {
    this.router.navigate(['/user/update-address/'+ id]);
  }
  updateContact(id: number) {
    this.router.navigate(['/user/update-contact/'+ id]);
  }


  removePhone(id: number) {
    this.contactsService.removePhone(id).subscribe(() => {
      this.contact.phoneNumbers = this.contact.phoneNumbers.filter(phone => phone.id != id)
    })
  }
  removeEmail(id: number) {
    this.contactsService.removeEmail(id).subscribe(() => {
      this.contact.emails = this.contact.emails.filter(email => email.id != id)
    })
  }
  removeAddress(id: number) {
    this.contactsService.removeAddress(id).subscribe(() => {
      this.contact.addresses = this.contact.addresses.filter(address => address.id != id)
    })
  }


}
