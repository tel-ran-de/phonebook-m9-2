import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegistrationComponent } from "./registration/registration.component";
import { ActivateEmailComponent } from "./activate-email/activate-email.component";
import { ActivationComponent } from "./activation/activation.component";
import { LoginComponent } from "./login/login.component";
import { UserPageComponent } from "./user-page/user-page.component";


const routes: Routes = [
  { path: '', redirectTo: 'user/registration', pathMatch:'full'},
  { path: 'user/registration', component: RegistrationComponent },
  { path: 'user/login', component: LoginComponent },
  { path: 'user/activate-email', component: ActivateEmailComponent},
  { path: 'user/activation/:token', component: ActivationComponent},
  { path: 'user/page', component: UserPageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
