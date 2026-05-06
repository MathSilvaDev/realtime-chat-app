export interface MessageResponse{
  content: string;
  sender: 'me' | 'contact';
  senderUsername: string;
  createdAt: string;
}