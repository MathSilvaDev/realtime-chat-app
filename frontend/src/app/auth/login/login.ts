import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-login',
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})

export class Login {

  constructor(
    private authService: AuthService,
    private router: Router
  ){}

  username: string = "";
  password: string = "";

  ngOnInit(){
    this.authService.removeToken();
  }

  login(){
    const username = this.replaceInput(this.username);
    const password = this.replaceInput(this.password);

    this.authService.login(username, password).subscribe({
      next: (jwt) => {
        this.authService.setToken(jwt.token)
        this.router.navigate(["home"]);
      },
      error: () => {
        alert(`Error logging into account.`);
      }
    });
  }

  replaceInput(str: string): string{
    return str.trim();
  }
}
