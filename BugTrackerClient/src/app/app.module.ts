import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {LoginComponent} from './components/login/login.component';
import {FormsModule} from '@angular/forms';
import {RegisterComponent} from './components/register/register.component';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {TicketsComponent} from './components/tickets/tickets.component';
import {ResetPasswordComponent} from './components/login/reset-password/reset-password.component';
import {LocaleDatePipe} from "./pipes/locale-date.pipe";
import { HomeComponent } from './components/home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    NavbarComponent,
    TicketsComponent,
    ResetPasswordComponent,
    LocaleDatePipe,
    HomeComponent
  ],

  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule,
    FontAwesomeModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {
}
