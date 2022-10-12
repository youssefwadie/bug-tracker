import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from "./components/register/register.component";
import {LoginRouteGuard} from "./services/guards/login.route.guard";
import {DashboardRouteGuard} from "./services/guards/dashboard.route.guard";
import {ResetPasswordComponent} from "./components/login/reset-password/reset-password.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {TicketListComponent} from "./components/ticket-list/ticket.list.component";
import {ProjectListComponent} from "./components/project-list/project.list.component";
import {ProjectDetailsComponent} from "./components/project-list/project-details/project.details.component";
import {AdminHomeComponent} from "./components/admin/admin-home/admin.home.component";
import {AdminRouteGuard} from "./services/guards/admin.route.guard.service";
import {ProjectManagementComponent} from "./components/admin/project-management/project.management.component";
import {UserListComponent} from "./components/admin/user-list/user.list.component";
import {UsersResolver} from "./resolvers/users.resolver";

const routes: Routes = [
  {path: "login", canActivate: [LoginRouteGuard], component: LoginComponent},
  {path: "reset", canActivate: [LoginRouteGuard], component: ResetPasswordComponent},
  {path: "register", canActivate: [LoginRouteGuard], component: RegisterComponent},
  {path: "", redirectTo: "/dashboard", pathMatch: "full"},
  {path: "dashboard", canActivate: [DashboardRouteGuard], component: DashboardComponent},
  {path: "tickets", canActivate: [DashboardRouteGuard], component: TicketListComponent},
  {
    path: "admin", canActivate: [AdminRouteGuard], component: AdminHomeComponent, children: [
      {path: '', redirectTo: "projects", pathMatch: "full"},
      {path: "projects", resolve: {organizationTeamMembers: UsersResolver}, component: ProjectManagementComponent},
      {path: "users", component: UserListComponent},
      {path: "**", redirectTo: "projects", pathMatch: "full"}
    ]
  },
  {path: "projects", canActivate: [DashboardRouteGuard], component: ProjectListComponent},
  {path: "project/:projectId", canActivate: [DashboardRouteGuard], component: ProjectDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
