import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {RegistrationComponent} from "./registration/registration.component";
import {ActivateEmailComponent} from "./activate-email/activate-email.component";
import {ActivationComponent} from "./activation/activation.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {PasswordRecoveryComponent} from "./password-recovery/password-recovery.component";

const routes: Routes = [
  {path: '', redirectTo: 'user/registration', pathMatch: 'full'},
  {path: 'user/registration', component: RegistrationComponent},
  {path: 'user/activate-email', component: ActivateEmailComponent},
  {path: 'user/activation/:token', component: ActivationComponent},
  {path: 'user/forgot-password', component: ForgotPasswordComponent},
  {path: 'user/password-recovery/:token', component: PasswordRecoveryComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
