import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private userService: UserService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authorizationValue = '';
    if (this.userService.getToken()) {
      authorizationValue = `Bearer ${this.userService.getToken()}`;
    }

    let tokenizedReq = req.clone({
      setHeaders: {
        Authorization: authorizationValue
      }
    });

    // console.log('Tokenized request...', authorizationValue);

    return next.handle(tokenizedReq);
  }
}
