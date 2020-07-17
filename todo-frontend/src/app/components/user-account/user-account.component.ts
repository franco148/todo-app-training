import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { HttpClient } from '@angular/common/http';

import { Subscription } from 'rxjs';

import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css']
})
export class UserAccountComponent implements OnInit, OnDestroy {

  accountForm: FormGroup;
  hidePassword = true;
  loginErrorMessage: string;
  isLoginOperation = true;

  onAuthenticationErrorSubscription: Subscription;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.cleanComponent();

    this.onAuthenticationErrorSubscription = this.userService.onAuthenticationErrorChanged
    .subscribe(errorMessage => {
      this.loginErrorMessage = errorMessage;
    });
  }

  onSubmit() {

    if (this.isLoginOperation) {
      this.userService.login({
        email: this.accountForm.value.email,
        password: this.accountForm.value.password
      });
    } else {
      this.userService.register({
        email: this.accountForm.value.email,
        password: this.accountForm.value.password
      });
    }

    this.cleanComponent();
  }

  changeOperation() {
    this.isLoginOperation = !this.isLoginOperation;
  }

  cleanComponent() {
    this.accountForm = new FormGroup({
      email: new FormControl('', { validators: [Validators.required, Validators.email] }),
      password: new FormControl('', { validators: [Validators.required, Validators.minLength(6)] })
    });
  }

  ngOnDestroy() {
    this.onAuthenticationErrorSubscription.unsubscribe();
  }
}
