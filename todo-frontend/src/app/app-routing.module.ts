import { NgModule } from '@angular/core';
import { Routes, RouterModule } from "@angular/router";
import { TaskListComponent } from './components/task-list/task-list.component';
import { UserAccountComponent } from './components/user-account/user-account.component';
import { AuthGuard } from './services/auth.guard';

const routes: Routes = [
  { path: '', component: TaskListComponent, canActivate: [AuthGuard] },
  { path: 'login', component: UserAccountComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
