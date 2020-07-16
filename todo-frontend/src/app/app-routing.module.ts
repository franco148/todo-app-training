import { NgModule } from '@angular/core';
import { Routes, RouterModule } from "@angular/router";
import { TaskListComponent } from './components/task-list/task-list.component';
import { UserAccountComponent } from './components/user-account/user-account.component';

const routes: Routes = [
  { path: '', component: TaskListComponent },
  { path: 'login', component: UserAccountComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
