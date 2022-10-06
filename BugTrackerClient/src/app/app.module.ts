import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {LoginComponent} from './components/login/login.component';
import {FormsModule} from '@angular/forms';
import {RegisterComponent} from './components/register/register.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatMenuModule} from '@angular/material/menu';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {TicketListComponent} from './components/ticket-list/ticket.list.component';
import {ResetPasswordComponent} from './components/login/reset-password/reset-password.component';
import {LocaleDatePipe} from "./pipes/locale-date.pipe";
import {ProjectListComponent} from './components/project-list/project.list.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ChartComponent} from './components/dashboard/chart/chart.component';
import {NgChartsModule} from "ng2-charts";
import {ProjectDetailsComponent} from "./components/project-list/project-details/project.details.component";
import {ProjectEditComponent} from "./components/admin/project-management/project-edit/project.edit.component";
import {NgSelectModule} from '@ng-select/ng-select';
import {AuthInterceptor} from "./interceptors/auth.interceptor";
import { ProjectManagementComponent } from './components/admin/project-management/project.management.component';
import { AdminHomeComponent } from './components/admin/admin-home/admin.home.component';
import { UserManagementComponent } from './components/admin/user-management/user.management.component';
import { UserEditComponent } from './components/admin/user-management/user-edit/user-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    NavbarComponent,
    DashboardComponent,
    ChartComponent,
    TicketListComponent,
    ResetPasswordComponent,
    LocaleDatePipe,
    ProjectListComponent,
    ProjectDetailsComponent,
    ProjectEditComponent,
    AdminHomeComponent,
    ProjectManagementComponent,
    UserManagementComponent,
    UserEditComponent,
  ],

  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule,
    FontAwesomeModule,
    MatToolbarModule,
    MatTableModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    BrowserAnimationsModule,
    NgChartsModule,
    NgSelectModule,
    MatPaginatorModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
