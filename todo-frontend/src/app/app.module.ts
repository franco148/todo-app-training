import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatMenuModule } from "@angular/material/menu";
import { MatDividerModule } from "@angular/material/divider";
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from "@angular/material/list";
import { MatDialogModule } from "@angular/material/dialog";

import { FlexLayoutModule } from "@angular/flex-layout";
import { NgxMaterialTimepickerModule } from "ngx-material-timepicker";

import { AppRoutingModule } from "./app-routing.module";

import { AppComponent } from './app.component';
import { UserAccountComponent } from './components/user-account/user-account.component';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import { TaskListComponent } from './components/task-list/task-list.component';
import { TaskItemCreatorComponent } from './components/task-list/task-item-creator/task-item-creator.component';
import { TaskItemEditComponent } from './components/task-list/task-item-edit/task-item-edit.component';
import { TaskItemOptionsComponent } from './components/task-list/task-item-options/task-item-options.component';
import { TaskListItemComponent } from './components/task-list/task-list-item/task-list-item.component';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { AuthInterceptorService } from './services/auth-interceptor.service';

@NgModule({
  declarations: [
    AppComponent,
    UserAccountComponent,
    SidenavComponent,
    TaskListComponent,
    TaskItemCreatorComponent,
    TaskItemEditComponent,
    TaskItemOptionsComponent,
    TaskListItemComponent,
    ToolbarComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatMenuModule,
    MatDividerModule,
    MatSidenavModule,
    MatListModule,
    MatDialogModule,
    NgxMaterialTimepickerModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    TaskItemEditComponent
  ]
})
export class AppModule { }
