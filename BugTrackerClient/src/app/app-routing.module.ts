import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from "./components/register/register.component";
import {HomeComponent} from "./components/home/home.component";
import {LoginRouteGuard} from "./services/guards/login-route.guard";
import {HomeRouteGuard} from "./services/guards/home-route.guard";
import {VerifyEmailComponent} from "./components/login/verify-email/verify-email.component";

const routes: Routes = [
  {path: 'login', canActivate: [LoginRouteGuard], component: LoginComponent},
  {path: 'confirm', canActivate: [LoginRouteGuard], component: VerifyEmailComponent},
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
