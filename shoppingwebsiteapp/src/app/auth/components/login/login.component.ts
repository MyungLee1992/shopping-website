import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../models/user';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'mw-app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
  }

  onLogin(user: User) {
    document.getElementById('closeLoginForm')?.click();
    var loginForm = <HTMLFormElement>document.getElementById('loginForm');
    this.authService.login(user).subscribe(
      (token: any) => {
        this.user = user;
        // Save token in local storage
        localStorage.setItem('jwtToken', token);

        // Clear input fields
        loginForm.reset();

        this.user = user;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

}
