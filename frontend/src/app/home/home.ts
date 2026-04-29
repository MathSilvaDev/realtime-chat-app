import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HomeService } from './service/home.service';
import { Router } from '@angular/router';
import { ContactResponse } from './dto/response/contact-response';
import { ContactRequest } from './dto/request/contact-request';

@Component({
  selector: 'app-home',
  imports: [ FormsModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {

  contactList: ContactResponse[] = [];

  constructor(
    private homeService: HomeService,
    private router: Router
  ){}

  ngOnInit(){
    this.loadContacts();
  }

  loadContacts(){
    this.homeService.findContacts().subscribe({
      next: (response) => {
        this.contactList = response;
      },
      error: (err) => {
        console.log(`ERROR: ${err}`);
      },
    });
  }

  addContact(contactName: string){

    const username: string = this.replaceInput(contactName);
    const request: ContactRequest = { username: username }

    this.homeService.addContact(request).subscribe({
      next: (response) => {
        this.contactList.push(response);
      },
      error: (err) => {
        console.log(`ERROR: ${err}`);
      },
    });
  }

  searchContact(){
    //this.homeService.findContact();
  }

  public removeContact(){
    //this.homeService.removeContact();
  }

  replaceInput(str: string): string{
    return str.trim();
  }

}
