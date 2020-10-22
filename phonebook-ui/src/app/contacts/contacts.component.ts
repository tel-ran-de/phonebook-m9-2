import { Component, OnInit } from '@angular/core';
import {Subscription} from "rxjs";
import {ContactsService} from "../service/contact.service";
import {Contact} from "../model/contact";

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {
  loading = true;
  searchString = '';
  contacts: Contact[] = [];
  pSub: Subscription;
  genderMale = "/assets/img/male.png";
  genderFemale = "/assets/img/female.jpg";

  constructor(private contactsService: ContactsService) { }

  ngOnInit() {
    this.pSub = this.contactsService.getAllContacts()
      .subscribe(() => {
        this.contacts = this.contactsService.contacts;
        this.loading = false;
      });
  }

  ngOnDestroy(){
    if (this.pSub){
      this.pSub.unsubscribe()
    }
  }



  removeContact(id: number) {
    this.contactsService.removeContact(id).subscribe(() =>{
      this.contacts = this.contacts.filter(post => post.id != id)
    })
  }
}
