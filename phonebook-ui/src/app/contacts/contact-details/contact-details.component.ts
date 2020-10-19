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
/*  addresses: any;
  phoneNumbers: any;
  emails: string;*/
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
      console.log(response);
      this.contact = response;
    });
  }

  removePhone(id: number) {
    this.contactsService.removePhone(id).subscribe(() => {
      this.contact.phoneNumbers = this.contact.phoneNumbers.filter(post => post.id != id)
    })
  }

  updatePhone(id: number) {
    this.router.navigate(['/user/updatephone/'+ id]);
  }

/*  submitForm() {
    this.contactsService.addContact(this.contact).subscribe((response) => {
      console.log(response)
      this.router.navigate(['/user/addphone/'+ response.id]);
    });

  }*/
}
