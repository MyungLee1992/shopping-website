import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Item } from '../models/item';
import { ItemService } from '../services/item.service';

@Component({
  selector: 'mw-item-list',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {

  public items: Item[];
  public editItem?: Item;
  public deleteItem?: Item;

  constructor(private itemService: ItemService) { }

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
    document.getElementById('addItemForm')?.click();
    this.itemService.addItem(addForm.value).subscribe(
      (response: Item) => {
        console.log(response);
        this.getItems();
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
}
