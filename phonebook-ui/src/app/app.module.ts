import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { ActivateEmailComponent } from './activate-email/activate-email.component';
import { ActivationComponent } from './activation/activation.component';
import { UserPageComponent } from './user-page/user-page.component';
import { UserPageDetailsComponent } from './user-page-details/user-page-details.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ActivateNewPasswordComponent } from './activate-new-password/activate-new-password.component';
import { PasswordRecoveryComponent } from './password-recovery/password-recovery.component';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    RegistrationComponent,
    ActivateEmailComponent,
    ActivationComponent,
    UserPageComponent,
    UserPageDetailsComponent,
    ForgotPasswordComponent,
    ActivateNewPasswordComponent,
    PasswordRecoveryComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    FormsModule,
    AppRoutingModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
