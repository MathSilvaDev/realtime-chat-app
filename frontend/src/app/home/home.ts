import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HomeService } from './service/home.service';
import { Router } from '@angular/router';
import { ContactResponse } from './dto/response/contact-response';
import { ContactRequest } from './dto/request/contact-request';
import { CommonModule } from '@angular/common';
import { ChatService } from './service/chat.service';

@Component({
  selector: 'app-home',
  imports: [FormsModule, CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {

  contactList: ContactResponse[] = [];

  messages: { content: string; sender: 'me' | 'contact' }[] = [];

  selectedContact?: ContactResponse;

  searchContactName: string = '';
  newContact: string = '';

  messageInput: string = '';

  constructor(
    private homeService: HomeService,
    private chatService: ChatService,
    protected router: Router
  ) {}

  ngOnInit(){
    this.findContacts();
  }

  findContacts(){
    this.homeService.findContacts().subscribe({
      next: (response) => {
        this.contactList = response;
      },
      error: (err) => {
        console.log(`ERROR: ${err}`);
      },
    });
  }

  addContact(){
    const username: string = this.replaceInput(this.newContact);
    const request: ContactRequest = { username: username }

    this.homeService.addContact(request).subscribe({
      next: (response) => {
        this.contactList.push(response);
        this.newContact = '';
      },
      error: (err) => {
        console.log(`ERROR: ${err}`);
      },
    });
  }

  loadMessages(contact: ContactResponse) {
    this.selectedContact = contact;
    this.messages = [];

    const chatId = contact.id;

    this.chatService.connect(chatId, (msg) => {
      this.messages.push({
        content: msg.message,
        sender: 'contact'
      });
    });
  }
  sendMessage() {
    if (!this.selectedContact || !this.messageInput.trim()) return;

    const chatId = this.selectedContact.id;

    this.chatService.send(chatId, this.messageInput);

    this.messages.push({
      content: this.messageInput,
      sender: 'me'
    });

    this.messageInput = '';
  }

  searchContact(){
    const contactUsername: string = this.replaceInput(this.searchContactName);
    const request: ContactRequest = { username: contactUsername }

    this.homeService.findContact(request).subscribe({
      next: (response) => {
        this.contactList = response;
      },
      error: (err) => {
        console.log(`ERROR: ${err}`);
      },
    });
  }

  replaceInput(str: string): string{
    return str.trim();
  }
}