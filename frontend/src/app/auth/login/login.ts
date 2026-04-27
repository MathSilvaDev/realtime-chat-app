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

  email: string = "";
  password: string = "";

  ngOnInit(){
    this.authService.clearLocalStorage();
  }

  login(){
    const email = this.replaceInput(this.email);
    const password = this.replaceInput(this.password);

    this.authService.login(email, password).subscribe({
      next: (jwt) => {
        this.authService.setToken(jwt.token)
        // this.router.navigate(["home"]);
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
