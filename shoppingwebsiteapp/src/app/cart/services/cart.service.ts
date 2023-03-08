import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from 'src/app/item/models/item';
import { environment } from 'src/environments/environment';
import { CartItem } from '../models/cartItem';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getCartItems(): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(`${this.apiServerUrl}/cart`);
  }

  public addCartItem(item: Item): Observable<CartItem> {
    return this.http.post<CartItem>(`${this.apiServerUrl}/cart/add`, item);
  }

  public updateCartItem(cartItem: CartItem): Observable<CartItem> {
    return this.http.put<CartItem>(`${this.apiServerUrl}/cart/update`, cartItem);
  }

  public deleteCartItem(cartItemId: Number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/cart/delete/${cartItemId}`);
  }

  public deleteCart(): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/cart/delete`);
  }

  public getCartItem(itemId: Number): Observable<Item> {
    return this.http.get<Item>(`${this.apiServerUrl}/item/${itemId}`);
  }
}
