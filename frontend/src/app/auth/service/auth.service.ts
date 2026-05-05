import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginResponseJwt } from '../dto/login-response-jwt';
import { Router } from '@angular/router';
import { ChatService } from '../../home/service/chat.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  
  constructor(
    private http: HttpClient,
    private router: Router,
    private chatService: ChatService
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

  setUserId(userId: string){
    localStorage.setItem("userId", userId);
  }

  removeToken(){
    localStorage.removeItem('token');
  }

  logout(){
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    this.chatService.disconnect();
  }
}
