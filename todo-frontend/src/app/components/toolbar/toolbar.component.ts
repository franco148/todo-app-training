import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';

import { Subscription } from 'rxjs';

import { UserModel } from 'src/app/data/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit, OnDestroy {

  @Output() sidenavToggle = new EventEmitter<void>();
  onIsAuthenticationSubscription: Subscription;

  loggedUser: UserModel;
  isUserAuthenticated = false;

  isToggleOpen = false;


  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.onIsAuthenticationSubscription = this.userService.onIsAuthenticationChanged
    .subscribe(authUser => {
      this.isUserAuthenticated = authUser != null;
      this.loggedUser = authUser;
    });

    this.isUserAuthenticated = this.userService.isUserAuthenticated();
    this.loggedUser = this.userService.getLoggedUser();
  }


  onToggleSidenav() {
    this.isToggleOpen = !this.isToggleOpen;
    this.sidenavToggle.emit();
  }

  onLogout() {
    this.userService.logout();
  }

  getAuthUserInitials() {
    let profileTextAvatar = this.loggedUser.email;

    if (this.loggedUser.firstName &&
        this.loggedUser.lastName) {

      profileTextAvatar = this.loggedUser.firstName.charAt(0) +
                          this.loggedUser.lastName.charAt(0);
    }

    return profileTextAvatar;
  }

  ngOnDestroy() {
    if (this.onIsAuthenticationSubscription) {
      this.onIsAuthenticationSubscription.unsubscribe();
    }
  }
}
