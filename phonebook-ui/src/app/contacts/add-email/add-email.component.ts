import { Component, OnInit } from '@angular/core';
import {ContactsService} from "../../service/contact.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-email',
  templateUrl: './add-email.component.html',
  styleUrls: ['./add-email.component.css']
})
export class AddEmailComponent implements OnInit {

  title = 'Add Email';
  AddEmailForm: FormGroup;
  loading: boolean;
  private contactId: number;

  constructor(private fb: FormBuilder,private contactsService: ContactsService, public router: Router, public activatedRoute: ActivatedRoute) {

    this.createForm();
  }

  ngOnInit(){
    this.contactId = this.activatedRoute.snapshot.params.contactId;
  }

  createForm() {
    this.AddEmailForm = this.fb.group({
      email: ['', [Validators.required, Validators.pattern("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,10}$")]]
    });
  }

  onSubmit() {
    this.loading = true;
    this.contactsService.addEmail(this.AddEmailForm.value,this.contactId)
      .subscribe((response) => {
          this.loading = false;
          this.router.navigate(['/user/contact/' + this.contactId]);
        },
        error => {
          this.loading = false;
        });
  }
}
