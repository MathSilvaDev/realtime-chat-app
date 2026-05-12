import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import { Observable } from 'rxjs';
import SockJS from 'sockjs-client';
import { MessageResponse } from '../dto/response/message-response';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private readonly API_URL = "/api/me/messages";
  private stompClient!: Client;

  constructor(private http: HttpClient){}

  connect(chatId: string, onMessage: (msg: any) => void) {

    if (this.stompClient && this.stompClient.active) {
      this.stompClient.deactivate();
    }

    const token = localStorage.getItem("token");
    const host = window.location.host;
    const protocol = window.location.protocol === 'https:' ? 'wss' : 'ws';

    this.stompClient = new Client({
      brokerURL: `${protocol}://${host}/ws?token=${token}`,
      reconnectDelay: 5000
    });

    this.stompClient.onConnect = () => {

      console.log("Connected");

      this.stompClient.subscribe(`/topic/chat/${chatId}`, message => {
        onMessage(JSON.parse(message.body));
      });
    };

    this.stompClient.onStompError = (frame) => {
      console.error("STOMP error:", frame);
    };

    this.stompClient.activate();
  }

  send(chatId: string, message: string){

    if (!this.stompClient || !this.stompClient.connected) {
      console.error("Disconnected");
      return;
    }

    this.stompClient.publish({
      destination: '/app/chat.send',
      body: JSON.stringify({
        contactId: chatId,
        message: message
      })
    });
  }

  disconnect(){
    if (this.stompClient) {
      this.stompClient.deactivate();
    }
  }

  loadMessages(contactId: string): Observable<MessageResponse[]>{
    return this.http.get<MessageResponse[]>(`${this.API_URL}/${contactId}`)
  }
}
