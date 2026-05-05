import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private stompClient!: Client;

  connect(chatId: string, onMessage: (msg: any) => void) {

    if (this.stompClient && this.stompClient.active) {
      this.stompClient.deactivate();
    }

    const token = localStorage.getItem("token");

    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      connectHeaders: {
        Authorization: `Bearer ${token}`
      },
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

  send(chatId: string, message: string) {

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

  disconnect() {
    if (this.stompClient) {
      this.stompClient.deactivate();
    }
  }
}
