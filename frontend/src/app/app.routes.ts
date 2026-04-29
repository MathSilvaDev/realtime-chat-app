import { Routes } from '@angular/router';
import { Register } from './auth/register/register';
import { Login } from './auth/login/login';
import { authGuard } from './auth/security/auth.guard';
import { PageNotFound } from './auth/error/page-not-found/page-not-found';
import { Home } from './home/home';

export const routes: Routes = [

  {path: "", redirectTo: "home", pathMatch: "full"},

  {path: "login", component: Login},
  {path: "register", component: Register},

  {path: "home", component: Home, canActivate: [authGuard]},
  
  {path: "**", component: PageNotFound}
];
