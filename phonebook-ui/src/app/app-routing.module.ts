import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegistrationComponent} from "./registration/registration.component";
import {AppComponent} from "./app.component";
import {ActivateEmailComponent} from "./activate-email/activate-email.component";
import {ActivationComponent} from "./activation/activation.component";
import {UserPageComponent} from "./user-page/user-page.component";
import {UserPageDetailsComponent} from "./user-page-details/user-page-details.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {ActivateNewPasswordComponent} from "./activate-new-password/activate-new-password.component";
import {PasswordRecoveryComponent} from "./password-recovery/password-recovery.component";


const routes: Routes = [
  { path: '', redirectTo: 'user/registration', pathMatch:'full'},
  { path: 'user/registration', component: RegistrationComponent},
  { path: 'user/login', component: LoginComponent},
  { path: 'user/ForgotPassword', component: ForgotPasswordComponent},
  { path: 'user/activateNewPassword', component: ActivateNewPasswordComponent},
  { path: 'user/PasswordRecovery', component: PasswordRecoveryComponent},
  { path: 'user/activatemail', component: ActivateEmailComponent},
  { path: 'user/activation', component: ActivationComponent},
  { path: 'user/page', component: UserPageComponent},
  { path: 'user/page/id/1', component: UserPageDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
