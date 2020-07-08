import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from "@angular/common/http";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";

import { AppComponent } from './app.component';
import { UserAccountComponent } from './components/user-account/user-account.component';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import { TaskListComponent } from './components/task-list/task-list.component';
import { TaskItemCreatorComponent } from './components/task-list/task-item-creator/task-item-creator.component';
import { TaskItemEditComponent } from './components/task-list/task-item-edit/task-item-edit.component';
import { TaskItemOptionsComponent } from './components/task-list/task-item-options/task-item-options.component';
import { TaskListItemComponent } from './components/task-list/task-list-item/task-list-item.component';

@NgModule({
  declarations: [
    AppComponent,
    UserAccountComponent,
    SidenavComponent,
    TaskListComponent,
    TaskItemCreatorComponent,
    TaskItemEditComponent,
    TaskItemOptionsComponent,
    TaskListItemComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
