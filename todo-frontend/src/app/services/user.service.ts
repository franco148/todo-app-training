import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserModel } from '../data/models/user.model';
import { Router } from '@angular/router';

import { Subject } from 'rxjs';

import { AuthResponse } from '../data/interfaces/auth-response';
import { SERVER_END_POINT_URL } from '../constants/app.constant';
import { AuthData } from '../data/interfaces/auth-data';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  serverUrl = `${SERVER_END_POINT_URL}`;

  onIsAuthenticationChanged = new Subject<UserModel>();
  onAuthenticationErrorChanged = new Subject<string>();

  constructor(private http: HttpClient,
              private router: Router,
              private snackBar: MatSnackBar) {
    if (localStorage.getItem('todo-auth-token') &&
        localStorage.getItem('todo-auth-user')) {
      this.onIsAuthenticationChanged.next(JSON.parse(localStorage.getItem('todo-auth-user')));
    }
  }

  login(authData: AuthData) {

    this.http.post<AuthResponse>(`${this.serverUrl}/login`, authData)
    .subscribe(authResponse => {
      console.log(authResponse);
      localStorage.setItem('todo-auth-token', authResponse.token);
      localStorage.setItem('todo-auth-user', JSON.stringify(authResponse.user));

      this.redirectEvent(authResponse.user, '');
    },
    authError => {
      // console.log('An error ocurred in Login process ', authError);
      // this.snackBar.open('Login Error', 'ERROR', { duration: 5000 })
      this.redirectEvent(null, 'login');
    });
  }

  register(authData: AuthData) {
    this.http.post<UserModel>(`${this.serverUrl}/users`, authData)
    .subscribe(registeredUser => {
      const successMessage = `User ${registeredUser.email} registered successfully, please login...`;
      this.snackBar.open(successMessage, 'SUCCESS', { duration: 5000 });
      this.router.navigate(['/login']);
    },
    registeredError => {
      console.log(registeredError);
      this.snackBar.open('The new account could not be created...', 'ERROR', { duration: 5000 });
    });
  }

  findById(userId: number) {
    this.http.get<UserModel>(`${this.serverUrl}/users/${userId}`)
    .subscribe(loggedUser => {
      // this.user = loggedUser;
      localStorage.setItem('todo-auth-user', JSON.stringify(loggedUser));
      this.redirectEvent(loggedUser, '');
    }, getUserError => console.log('Error getting user with ID=' + userId));
  }

  logout() {
    if (localStorage.getItem('todo-auth-token')) {
      localStorage.removeItem('todo-auth-token');
    }

    if (localStorage.getItem('todo-auth-user')) {
      localStorage.removeItem('todo-auth-user');
    }

    this.onIsAuthenticationChanged.next(null);
    this.router.navigate(['/login']);
  }

  isUserAuthenticated() {
    return !!localStorage.getItem('todo-auth-token') &&
           !!localStorage.getItem('todo-auth-user');
  }

  getToken() {
    if (localStorage.getItem('todo-auth-token')) {
      return localStorage.getItem('todo-auth-token');
    }

    return null;
  }

  getLoggedUser(): UserModel {
    if (localStorage.getItem('todo-auth-user')) {
      return JSON.parse(localStorage.getItem('todo-auth-user'));
    }

    return null;
  }

  private redirectEvent(authenticatedUser: UserModel, path: string) {
    if (authenticatedUser) {
      this.onIsAuthenticationChanged.next(authenticatedUser);
    } else {
      const loginErrorMessage = "Invalid email or password, please try again."
      this.onAuthenticationErrorChanged.next(loginErrorMessage);
    }
    console.log('Redirecting to: ', path);
    this.router.navigate([`/${path}`]);
  }
}
