import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AppComponent} from './app.component';
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {PasswordRecoveryComponent} from "./password-recovery/password-recovery.component";
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {RegistrationComponent} from './registration/registration.component';
import {ActivateEmailComponent} from './activate-email/activate-email.component';
import {ActivationComponent} from './activation/activation.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {UserService} from "./service/user.service";
import {UserPageComponent} from "./user-page/user-page.component";
import {LoginComponent} from "./login/login.component";
import {LogoutComponent} from "./logout/logout.component";
import {TokenHttpInterceptor} from "./service/TokenHttpInterceptor";
import { ContactsComponent } from './contacts/contacts.component';
import {ContactsFilterPipe} from "./pipes/contacts-filter.pipe";
import { AddContactComponent } from './contacts/add-contact/add-contact.component';
import { AddPhoneComponent } from './contacts/add-phone/add-phone.component';
import { ContactDetailsComponent } from './contacts/contact-details/contact-details.component';
import { AddAddressComponent } from './contacts/add-address/add-address.component';
import { UpdatePhoneComponent } from './contacts/update-phone/update-phone.component';
import { AddEmailComponent } from './contacts/add-email/add-email.component';
import { UpdateEmailComponent } from './contacts/update-email/update-email.component';
import { UpdateAddressComponent } from './contacts/update-address/update-address.component';
import { UpdateContactComponent } from './contacts/update-contact/update-contact.component';
import { ChangePasswordComponent } from './user-page/change-password/change-password.component';

@NgModule({
  declarations: [
    AppComponent,
    ForgotPasswordComponent,
    PasswordRecoveryComponent,
    HeaderComponent,
    FooterComponent,
    RegistrationComponent,
    ActivateEmailComponent,
    ActivationComponent,
    UserPageComponent,
    LoginComponent,
    LogoutComponent,
    ContactsComponent,
    ContactsFilterPipe,
    AddContactComponent,
    AddPhoneComponent,
    ContactDetailsComponent,
    AddAddressComponent,
    UpdatePhoneComponent,
    AddEmailComponent,
    UpdateEmailComponent,
    UpdateAddressComponent,
    UpdateContactComponent,
    ChangePasswordComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    FormsModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: TokenHttpInterceptor, multi: true}, UserService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
