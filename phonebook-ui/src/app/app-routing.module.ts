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
  {path: 'user/addNewContact', component: AddContactComponent, canActivate: [AuthGaurdService]},
  {path: 'user/addphone/:contactId', component: AddPhoneComponent, canActivate: [AuthGaurdService]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
