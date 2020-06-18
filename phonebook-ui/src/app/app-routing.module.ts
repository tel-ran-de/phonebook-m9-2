import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegistrationComponent } from "./registration/registration.component";
import { ActivateEmailComponent } from "./activate-email/activate-email.component";
import { ActivationComponent } from "./activation/activation.component";

const routes: Routes = [
  { path: 'user/registration', component: RegistrationComponent },
  { path: 'user/activatemail', component: ActivateEmailComponent},
  { path: 'user/activation', component: ActivationComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
