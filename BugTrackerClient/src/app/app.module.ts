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
import {TicketListComponent} from './components/ticket-list/ticket.list.component';
import {ResetPasswordComponent} from './components/login/reset-password/reset-password.component';
import {LocaleDatePipe} from "./pipes/locale-date.pipe";
import { HomeComponent } from './components/home/home.component';
import { ProjectListComponent } from './components/project-list/project.list.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { ChartComponent } from './components/dashboard/chart/chart.component';
import {NgChartsModule} from "ng2-charts";
import {ProjectDetailsComponent} from "./components/project-list/project-details/project-details.component";
import {ProjectEditComponent} from "./components/project-list/project-edit/project.edit.component";

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        RegisterComponent,
        NavbarComponent,
        HomeComponent,
        DashboardComponent,
        ChartComponent,
        TicketListComponent,
        ResetPasswordComponent,
        LocaleDatePipe,
        HomeComponent,
        ProjectListComponent,
        ProjectDetailsComponent,
        ProjectEditComponent,
    ],

  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule,
    FontAwesomeModule,
    BrowserAnimationsModule,
    NgChartsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {
}
