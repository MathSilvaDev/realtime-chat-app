import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginResponseJwt } from '../models/login-response-jwt';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  
  constructor(
    private http: HttpClient,
    private router: Router
  ){}

  private readonly API_URL = "/api/auth";

  register(username: string, email: string, password: string): Observable<void>{
    return this.http.post<void>(`${this.API_URL}/register`,{
      username, email, password
    });
  }

  login(email: string, password: string): Observable<LoginResponseJwt>{
    return this.http.post<LoginResponseJwt>(`${this.API_URL}/login`,{
      email, password 
    })
  }

  setToken(token: string){
    localStorage.setItem("token", token);
  }

  setUsername(username: string){
    localStorage.setItem("username", username);
  }

  setInfoLogin(username: string, token: string){
    this.setUsername(username);
    this.setToken(token);
  }

  clearLocalStorage(){
    localStorage.clear();
  }

  logout(){
    this.clearLocalStorage();
    this.router.navigate(["/login"])
  }
}
