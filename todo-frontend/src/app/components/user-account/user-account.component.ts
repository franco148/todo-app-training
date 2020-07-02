import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css']
})
export class UserAccountComponent implements OnInit {

  accountForm: FormGroup;
  hide = true;

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.accountForm = new FormGroup({
      email: new FormControl(''),
      password: new FormControl('')
    });
  }

  onSubmit() {

    const userAccount = {
      email: this.accountForm.value.email,
      password: this.accountForm.value.password
    };
    console.log('On Subtmit clicked!!!', userAccount);
    this.httpClient.post(`http://localhost:8080/users`, userAccount)
      .subscribe(response => console.log('Account has been created!!!', response));
  }

}
