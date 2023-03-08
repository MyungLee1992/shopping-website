import { HttpErrorResponse } from '@angular/common/http';
import { Component, Injectable, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { CartItem } from 'src/app/cart/models/cartItem';
import { CartService } from 'src/app/cart/services/cart.service';
import { Item } from '../models/item';
import { ItemService } from '../services/item.service';

@Component({
  selector: 'mw-item-list',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
@Injectable()
export class ItemComponent implements OnInit {

  public items: Item[];
  public addedToCartItem?: Item;
  public editItem?: Item;
  public deleteItem?: Item;

  constructor(private itemService: ItemService, private cartService: CartService) { }

  ngOnInit(): void {
    this.getItems();
  }
  
  public getItems(): void {
    this.itemService.getItems().subscribe(
      (response: Item[]) => {
        this.items = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddItem(addForm: NgForm): void {
    document.getElementById('closeItemForm')?.click();
    var addItemForm = <HTMLFormElement>document.getElementById('addItemForm');
    this.itemService.addItem(addForm.value).subscribe(
      (response: Item) => {
        this.getItems();
        window.location.reload();
        addItemForm?.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public onUpdateItem(item: Item): void {
    document.getElementById('editItemForm')?.click();
    this.itemService.updateItem(item).subscribe(
      (response: Item) => {
        console.log(response);
        this.getItems();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public onDeleteItem(itemId?: number): void {
    if (itemId == null) {
      return;
    }

    this.itemService.deleteItem(itemId).subscribe(
      (response: void) => {
        console.log(response);
        this.getItems();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public searchItems(key: string): void {
    const results: Item[] = [];
    for (const item of this.items) {
      if (item.name.toLowerCase().indexOf(key.toLowerCase()) !== -1 || item.description.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(item);
      }
    }

    this.items = results;
    if (results.length === 0 || !key) {
      this.getItems();
    }
  }

  public onOpenModal(mode: string, item?: Item): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addItemModal')
    } else if (mode === 'edit') {
      this.editItem = item;
      button.setAttribute('data-target', '#editItemModal')
    } else if (mode === 'delete') {
      this.deleteItem = item;
      button.setAttribute('data-target', '#deleteItemModal')
    }

    container?.appendChild(button);
    button.click();
  }

  public addToCart(item: Item): void {
    var cartAlert = document.getElementById('alert');
    this.cartService.addCartItem(item).subscribe(
      (response: CartItem) => {
        this.addedToCartItem = item;
        setTimeout(() => {
          cartAlert!.hidden = true;
        }, 2000);
        cartAlert!.hidden = false;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
