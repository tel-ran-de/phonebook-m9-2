import {Component, OnInit} from '@angular/core';
import {ContactsService} from "../../service/contact.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Contact} from "../../model/contact";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-update-email',
  templateUrl: './update-email.component.html',
  styleUrls: ['./update-email.component.css']
})
export class UpdateEmailComponent implements OnInit {

  title = 'Update Email';
  UpdateEmailForm: FormGroup;
  loading: boolean;
  contact: Contact
  emailId: number;

  constructor(private fb: FormBuilder, private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute) {
    this.contact = new Contact();
    this.createForm();
  }

  ngOnInit() {
    this.emailId = this.activatedRoute.snapshot.params.emailId;
    this.contactsService.getEmail(this.emailId).subscribe(response => {
      this.contact = response
      this.setDefault();
    });

  }

  setDefault() {

    let email = {
      email: this.contact.email
    };
    this.UpdateEmailForm.setValue(email);
  }

  createForm() {
    this.UpdateEmailForm = this.fb.group({
      email: ['', [Validators.required, Validators.pattern("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,10}$")]]
    });

  }


  onSubmit() {
    this.loading = true;
    this.contactsService.updateEmail(this.UpdateEmailForm.value, this.contact.contactId, this.emailId)
      .subscribe((response) => {
        this.loading = false;
        this.router.navigate(['/user/contact/' + this.contact.contactId]);
      }, error => {
        this.loading = false;
      });

  }

}
