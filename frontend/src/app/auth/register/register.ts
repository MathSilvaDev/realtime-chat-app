import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-register',
  imports: [FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {

  constructor(
    private authService: AuthService,
    private router: Router
  ){}

  username: string = "";
  email: string = "";
  password: string = "";

  ngOnInit(){
    this.authService.clearLocalStorage();
  }

  register(){
    const username = this.replaceInput(this.username);
    const email = this.replaceInput(this.email);
    const password = this.replaceInput(this.password);

    this.authService.register(username, email, password).subscribe({
      next: () => {
        this.router.navigate(["/login"]);
      },
      error: () => {
        alert(`Error creating account.`);
      }
    })
  }

  replaceInput(str: string): string{
    return str.trim();
  }
}
