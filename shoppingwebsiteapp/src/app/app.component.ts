import { Component, OnInit, } from '@angular/core';
import { User } from './auth/models/user';
import { AuthService } from './auth/services/auth.service';

@Component({
  selector: 'mw-app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit { 
  user: User;

  constructor(authService: AuthService) {
  }

  ngOnInit(): void {
    
  }

}

