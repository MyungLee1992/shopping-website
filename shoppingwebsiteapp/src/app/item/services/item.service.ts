import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Item } from '../models/item';

@Injectable({ providedIn: 'root' })
export class ItemService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getItems(): Observable<Item[]> {
    return this.http.get<Item[]>(`${this.apiServerUrl}/item/all`);
  }

  public getItemById(itemId: Number): Observable<Item> {
    return this.http.get<Item>(`${this.apiServerUrl}/item/find/${itemId}`);
  }

  public addItem(item: Item): Observable<Item> {
    return this.http.post<Item>(`${this.apiServerUrl}/item/add`, item);
  }

  public updateItem(item: Item): Observable<Item> {
    return this.http.put<Item>(`${this.apiServerUrl}/item/update`, item);
  }

  public deleteItem(itemId: Number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/item/delete/${itemId}`);
  }

}
