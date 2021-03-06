import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {RegistrationComponent} from "./registration/registration.component";
import {ActivateEmailComponent} from "./activate-email/activate-email.component";
import {ActivationComponent} from "./activation/activation.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {PasswordRecoveryComponent} from "./password-recovery/password-recovery.component";
import {UserPageComponent} from "./user-page/user-page.component";
import {AuthGaurdService} from "./service/auth-gaurd.service";
import {LoginComponent} from "./login/login.component";
import {LogoutComponent} from "./logout/logout.component";
import {AddContactComponent} from "./contacts/add-contact/add-contact.component";
import {AddPhoneComponent} from "./contacts/add-phone/add-phone.component";
import {ContactDetailsComponent} from "./contacts/contact-details/contact-details.component";
import {AddAddressComponent} from "./contacts/add-address/add-address.component";
import {UpdatePhoneComponent} from "./contacts/update-phone/update-phone.component";
import {AddEmailComponent} from "./contacts/add-email/add-email.component";
import {UpdateEmailComponent} from "./contacts/update-email/update-email.component";
import {UpdateAddressComponent} from "./contacts/update-address/update-address.component";
import {UpdateContactComponent} from "./contacts/update-contact/update-contact.component";
import {ChangePasswordComponent} from "./user-page/change-password/change-password.component";
import {UserProfileComponent} from "./user-page/user-profile/user-profile.component";

const routes: Routes = [
  {path: '', component: UserPageComponent, canActivate: [AuthGaurdService]},
  {path: 'user/registration', component: RegistrationComponent},
  {path: 'user/activate-email', component: ActivateEmailComponent},
  {path: 'user/activation/:token', component: ActivationComponent},
  {path: 'user/forgot-password', component: ForgotPasswordComponent},
  {path: 'user/password-recovery/:token', component: PasswordRecoveryComponent},
  {path: 'user/logout', component: LogoutComponent, canActivate: [AuthGaurdService]},
  {path: 'user/login', component: LoginComponent},
  {path: 'user/contact/:id', component: ContactDetailsComponent, canActivate: [AuthGaurdService]},
  {path: 'user/add-NewContact', component: AddContactComponent, canActivate: [AuthGaurdService]},
  {path: 'user/add-phone/:contactId', component: AddPhoneComponent, canActivate: [AuthGaurdService]},
  {path: 'user/add-address/:contactId', component: AddAddressComponent, canActivate: [AuthGaurdService]},
  {path: 'user/add-email/:contactId', component: AddEmailComponent, canActivate: [AuthGaurdService]},
  {path: 'user/update-phone/:phoneId', component: UpdatePhoneComponent, canActivate: [AuthGaurdService]},
  {path: 'user/update-email/:emailId', component: UpdateEmailComponent, canActivate: [AuthGaurdService]},
  {path: 'user/update-address/:addressId', component: UpdateAddressComponent, canActivate: [AuthGaurdService]},
  {path: 'user/update-contact/:contactId', component: UpdateContactComponent, canActivate: [AuthGaurdService]},
  {path: 'user/account/password', component: ChangePasswordComponent, canActivate: [AuthGaurdService]},
  {path: 'user/profile/:id', component: UserProfileComponent, canActivate: [AuthGaurdService]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
