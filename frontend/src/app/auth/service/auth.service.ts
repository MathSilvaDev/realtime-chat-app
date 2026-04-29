import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
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

  register(name: string, username: string, password: string): Observable<void>{
    return this.http.post<void>(`${this.API_URL}/register`,{
      name, username, password
    });
  }

  login(username: string, password: string): Observable<LoginResponseJwt>{
    return this.http.post<LoginResponseJwt>(`${this.API_URL}/login`,{
      username, password 
    })
  }

  setToken(token: string){
    localStorage.setItem("token", token);
  }

  removeToken(){
    localStorage.removeItem('token');
  }

  logout(){
    localStorage.clear()
    this.router.navigate(["/login"])
  }
}
