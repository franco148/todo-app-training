import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  // loggedUser:
  isToggleOpen = false;

  constructor() { }

  ngOnInit(): void {
  }

  onToggleSidenav() {
    this.isToggleOpen = !this.isToggleOpen;
  }
}
