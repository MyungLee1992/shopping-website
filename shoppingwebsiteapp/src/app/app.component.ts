import { Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewChildren } from '@angular/core';
import { User } from './auth/models/user';
import { AuthService } from './auth/services/auth.service';

@Component({
  selector: 'mw-app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit { 
  user = new User();
  public cartCount: number;

  constructor(private authService: AuthService) {
    this.login(this.user);
  }

  ngOnInit(): void {
  }

  // register(user: User) {
  //   this.authService.register(user).subscribe();
  // }

  login(user: User) {
    this.authService.login(user).subscribe((token: string) => {
      localStorage.setItem('jwtToken', token);
    });
  }
}

