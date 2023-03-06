import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { ItemComponent } from './item/components/item.component';
import { AuthIntercepter } from './auth/auth.intercepter';
import { LoginComponent } from './auth/components/login/login.component';
import { CartComponent } from './cart/components/cart.component';

@NgModule({
  declarations: [
    AppComponent,
    ItemComponent,
    LoginComponent,
    CartComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthIntercepter,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
