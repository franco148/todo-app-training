import { Component, OnInit } from '@angular/core';

import { Subscription } from 'rxjs';

import { TaskModel } from 'src/app/data/models/task.model';
import { TaskService } from 'src/app/services/task.service';
import { UserService } from 'src/app/services/user.service';
import { TaskOperation } from 'src/app/data/enums/task-operation.enum';
import { State } from 'src/app/data/enums/state.enum';
import { TaskChange } from 'src/app/data/interfaces/task-change';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

  showAddTaskComponent: boolean = false;
  showMainDefaultEmptyPanel: boolean = true;

  inProgressTasksList: TaskModel[] = [];
  plannedPausedTasksList: TaskModel[] = [];
  completedCancelledTasksList: TaskModel[] = [];

  onTaskSaveUpdateSubscription: Subscription;

  constructor(private userService: UserService, private taskService: TaskService) { }

  ngOnInit(): void {
    // Listening the changes when adding new tasks
    this.onTaskSaveUpdateSubscription = this.taskService.onTaskSaveUpdateChanged
    .subscribe(operationResult => {
      this.addOrMoveTask(operationResult);
    });

    // Getting today's tasks if exist
    this.taskService.getTodaysUserTasks(this.userService.getLoggedUser().id)
    .subscribe(todaysTasks => {
      for (const task of todaysTasks) {
        const taskOperation: TaskChange = {
          operation: TaskOperation.SAVED,
          prevState: State.PLANNED,
          task: task
        };
        this.addOrMoveTask(taskOperation);
      }

      // verifying if there are planned tasks for today
      this.showMainDefaultEmptyPanel = !this.anyTaskPlannedForToday();
    });
  }


  onEnableAddTaskComponent(enable: boolean) {
    this.showAddTaskComponent = enable;
  }

  anyTaskPlannedForToday() {
    return this.inProgressTasksList.length > 0 ||
           this.plannedPausedTasksList.length > 0 ||
           this.completedCancelledTasksList.length > 0;
  }

  private addOrMoveTask(taskChange: TaskChange) {
    if (taskChange.operation === TaskOperation.UPDATED) {
      // If task is being moved from one to another state,
      // remove it from the source array
      this.removeItemFromSection(taskChange.prevState, taskChange.task);
      this.addTaskToSection(taskChange.task);
    } else {
      this.addTaskToSection(taskChange.task);
    }

    this.showAddTaskComponent = false;
    this.showMainDefaultEmptyPanel = !this.anyTaskPlannedForToday();
  }

  private removeItemFromSection(state: State, updatedTask: TaskModel) {
    if (state === State.PLANNED || state === State.PAUSED) {
      const taskIndex = this.plannedPausedTasksList.findIndex(task => task.id === updatedTask.id);
      if (taskIndex !== -1) {
        this.plannedPausedTasksList.splice(taskIndex, 1);
      }
    } else if (state === State.IN_PROGRESS) {
      const taskIndex = this.inProgressTasksList.findIndex(task => task.id === updatedTask.id);
      if (taskIndex !== -1) {
        this.inProgressTasksList.splice(taskIndex, 1);
      }
    } else {
      const taskIndex = this.completedCancelledTasksList.findIndex(task => task.id === updatedTask.id);
      if (taskIndex !== -1) {
        this.completedCancelledTasksList.splice(taskIndex, 1);
      }
    }
  }

  private addTaskToSection(task: TaskModel) {
    if (task.state === State.PLANNED || task.state === State.PAUSED) {
      this.plannedPausedTasksList.push(task);
    } else if (task.state === State.IN_PROGRESS) {
      this.inProgressTasksList.push(task);
    } else {
      this.completedCancelledTasksList.push(task);
    }
  }

}
