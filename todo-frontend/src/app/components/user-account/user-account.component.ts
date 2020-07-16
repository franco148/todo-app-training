import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";
import { HttpClient } from '@angular/common/http';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css']
})
export class UserAccountComponent implements OnInit, OnDestroy {

  accountForm: FormGroup;
  hide = true;
  isLoginOperation = true;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.cleanComponent();
  }

  onSubmit() {

    const userAccount = {
      email: this.accountForm.value.email,
      password: this.accountForm.value.password
    };

    if (this.isLoginOperation) {
      this.userService.login(userAccount);
    } else {
      this.userService.register(userAccount);
    }

  }

  changeOperation() {
    this.isLoginOperation = !this.isLoginOperation;
  }

  cleanComponent() {
    this.accountForm = new FormGroup({
      email: new FormControl(''),
      password: new FormControl('')
    });
  }

  ngOnDestroy() {

  }
}
