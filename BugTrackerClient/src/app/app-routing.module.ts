import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from "./components/register/register.component";
import {LoginRouteGuard} from "./services/guards/login-route.guard";
import {HomeRouteGuard} from "./services/guards/home-route.guard";
import {ResetPasswordComponent} from "./components/login/reset-password/reset-password.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {TicketListComponent} from "./components/ticket-list/ticket.list.component";
import {ProjectListComponent} from "./components/project-list/project.list.component";

const routes: Routes = [
  {path: "login", canActivate: [LoginRouteGuard], component: LoginComponent},
  {path: "reset", canActivate: [LoginRouteGuard], component: ResetPasswordComponent},
  {path: "register", canActivate: [LoginRouteGuard], component: RegisterComponent},
  {path: "", redirectTo: "/dashboard", pathMatch: "full"},
  {path: "dashboard", canActivate: [HomeRouteGuard], component: DashboardComponent},
  {path: "tickets", canActivate: [HomeRouteGuard], component: TicketListComponent},
  {path: "projects", canActivate: [HomeRouteGuard], component: ProjectListComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
