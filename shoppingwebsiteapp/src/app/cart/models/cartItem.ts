import { Item } from "src/app/item/models/item";
import { Cart } from "./cart";

export class CartItem {
    id: number;
    cart: Cart;
    item: Item;
    quantity: number;
    price: number;
}
