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

    const socket = new SockJS('http://localhost:8080/ws');

    this.stompClient = new Client({
      webSocketFactory: () => socket,
      connectHeaders: {
        Authorization: `Bearer ${token}`
      },
      reconnectDelay: 5000 // reconecta automático
    });

    this.stompClient.onConnect = () => {

      console.log("✅ WebSocket conectado");

      this.stompClient.subscribe(`/topic/chat/${chatId}`, message => {
        onMessage(JSON.parse(message.body));
      });
    };

    this.stompClient.onStompError = (frame) => {
      console.error("Erro STOMP:", frame);
    };

    this.stompClient.activate();
  }

  send(chatId: string, message: string) {

    if (!this.stompClient || !this.stompClient.connected) {
      console.error("❌ WebSocket não conectado");
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