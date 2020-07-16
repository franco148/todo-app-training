import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient,
              private snackBar: MatSnackBar) {
    //
  }

  login(authData) {

    this.http.post('http://localhost:8080/login', authData)
    .subscribe((authResponse: any) => {
      console.log(authResponse);
      localStorage.setItem('todo-auth-token', authResponse.token);
      localStorage.setItem('todo-auth-user', JSON.stringify(authResponse.user));
    },
    authError => {
      console.log('An error ocurred in Login process ', authError);
      this.snackBar.open('Login Error', 'ERROR', { duration: 5000 })
    });
  }

  register(authData) {
    this.http.post('http://localhost:8080/users', authData)
    .subscribe(registeredUser => {
      console.log(registeredUser);
    },
    registeredError => {
      console.log('Registered error: ', registeredError);
      this.snackBar.open('Register Error', 'ERROR', { duration: 5000 })
    });
  }
}
