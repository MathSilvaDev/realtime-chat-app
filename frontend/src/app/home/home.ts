import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HomeService } from './service/home.service';
import { Router } from '@angular/router';
import { ContactResponse } from './dto/response/contact-response';
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

  messages: {
    content: string;
    sender: 'me' | 'contact';
    senderUsername: string;
    createdAt: string;
  }[] = [];

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
      error: (err) => console.log(err),
    });
  }

  addContact(){
    const username = this.replaceInput(this.newContact);

    this.homeService.addContact({ username }).subscribe({
      next: (response) => {
        this.contactList.push(response);
        this.newContact = '';
      },
      error: (err) => console.log(err),
    });
  }

  loadMessages(contact: ContactResponse) {
    this.selectedContact = contact;
    this.messages = [];

    const myId = localStorage.getItem("userId");
    if (!myId) return;

    const chatId = this.getChatId(myId, contact.id);

    this.chatService.connect(chatId, (msg) => {

      this.messages.push({
        content: msg.content,
        sender: msg.senderId === myId ? 'me' : 'contact',
        senderUsername: msg.senderUsername,
        createdAt: msg.createdAt
      });

    });
  }

  sendMessage() {
    if (!this.selectedContact || !this.messageInput.trim()) return;

    this.chatService.send(this.selectedContact.id, this.messageInput);

    this.messageInput = '';
  }

  searchContact(){
    const username = this.replaceInput(this.searchContactName);

    this.homeService.findContact({ username }).subscribe({
      next: (response) => this.contactList = response,
      error: (err) => console.log(err),
    });
  }

  replaceInput(str: string): string{
    return str.trim();
  }

  canSendMessage(): boolean {
    return !!this.selectedContact && !!this.messageInput.trim();
  }

  formatMessageDate(createdAt: string): string {
    if (!createdAt) return '';

    return new Intl.DateTimeFormat('en', {
      month: 'short',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    }).format(new Date(createdAt));
  }

  private getChatId(firstUserId: string, secondUserId: string): string {
    return [firstUserId, secondUserId].sort().join('_');
  }
}
