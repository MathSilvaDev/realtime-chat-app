import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserInfoResponse } from '../dto/response/user-info-response';
import { ContactResponse } from '../dto/response/contact-response';
import { ContactRequest } from '../dto/request/contact-request';

@Injectable({
  providedIn: 'root',
})
export class HomeService {
  
  private readonly API_URL_USER = '/api/me'
  private readonly API_URL_CONTACTS = '/api/me/contacts'

  constructor(private http: HttpClient){}

  public getMyInfo(): Observable<UserInfoResponse>{
    return this.http.get<UserInfoResponse>(this.API_URL_USER);
  }

  public findContacts(): Observable<ContactResponse[]>{
    return this.http.get<ContactResponse[]>(this.API_URL_CONTACTS);
  }

  public findContact(){}

  public addContact(contactRequest: ContactRequest): Observable<ContactResponse> {
    return this.http.post<ContactResponse>(this.API_URL_CONTACTS, {...contactRequest} );
  }

  public removeContact(){}

}
