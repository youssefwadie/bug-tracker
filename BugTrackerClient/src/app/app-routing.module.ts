import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from "./components/register/register.component";
import {LoginRouteGuard} from "./services/guards/login-route.guard";
import {HomeRouteGuard} from "./services/guards/home-route.guard";
import {ResetPasswordComponent} from "./components/login/reset-password/reset-password.component";
import {HomeComponent} from "./components/home/home.component";

const routes: Routes = [
  {path: 'login', canActivate: [LoginRouteGuard], component: LoginComponent},
  {path: 'reset', canActivate: [LoginRouteGuard], component: ResetPasswordComponent},
  {path: 'register', component: RegisterComponent},
  {path: '', canActivate: [HomeRouteGuard], component: HomeComponent},
  {path: 'dashboard', canActivate: [HomeRouteGuard], component: HomeComponent},
  {path: 'tickets', canActivate: [HomeRouteGuard], component: HomeComponent},
  {path: 'administration', canActivate: [HomeRouteGuard], component: HomeComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
