import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegistrationComponent} from "./registration/registration.component";
import {AppComponent} from "./app.component";
import {ActivateEmailComponent} from "./activate-email/activate-email.component";
import {ActivationComponent} from "./activation/activation.component";
import {UserPageComponent} from "./user-page/user-page.component";
import {UserPageDetailsComponent} from "./user-page-details/user-page-details.component";


const routes: Routes = [
  { path: '', redirectTo: 'user/registration', pathMatch:'full'},
  { path: 'user/registration', component: RegistrationComponent},
  { path: 'user/login', component: LoginComponent},
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
