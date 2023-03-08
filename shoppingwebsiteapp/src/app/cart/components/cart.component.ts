import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CartItem } from '../models/cartItem';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'mw-app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  public cartItems: CartItem[];
  public totalPrice: number = 0;

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
    this.getCart();
  }

  public getCart(): void {
    this.cartService.getCartItems().subscribe(
      (response: CartItem[]) => {
        this.cartItems = response;
        this.totalPrice = 0;
        this.cartItems.forEach((cartItem: CartItem) => {
          this.totalPrice += Number(cartItem.price.toFixed(2));
        });
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onUpdateCartItem(cartItem: CartItem, event: any): void {
    var quantity = event.target.value;
    cartItem.quantity = quantity;
    cartItem.price = cartItem.item.price * quantity;
    this.cartService.updateCartItem(cartItem).subscribe(
      (response: CartItem) => {
        this.getCart();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteCartItem(cartItemId: Number): void {
    if (cartItemId == null) {
      return;
    }

    this.cartService.deleteCartItem(cartItemId).subscribe(
      (response: void) => {
        this.getCart();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public onClearCart(): void {
    this.cartService.deleteCart().subscribe(
      (response: void) => {
        this.getCart();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

}
