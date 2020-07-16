import { Component, OnInit, Inject, Input } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { TaskService } from 'src/app/services/task.service';
import { State } from '../../../data/enums/state.enum';
import { TaskModel } from 'src/app/data/models/task.model';
import { TaskChange } from 'src/app/data/interfaces/task-change';
import { TaskOperation } from 'src/app/data/enums/task-operation.enum';

@Component({
  selector: 'app-task-item-options',
  templateUrl: './task-item-options.component.html',
  styleUrls: ['./task-item-options.component.css']
})
export class TaskItemOptionsComponent implements OnInit {

  @Input() taskId: number;
  @Input() currentState: State;

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
  }

  // onPlay() {
  //   // console.log('Moving the task to IN_PROGRESS');
  //   this.taskService.changeTaskState(this.taskId, State.IN_PROGRESS)
  //   .subscribe(updatedTask => {
  //     // console.log("Updated task", updatedTask);
  //     this.notifyChange(updatedTask);
  //   });
  // }

  // onPause() {
  //   // console.log('Moving the task to PAUSE');
  //   this.taskService.changeTaskState(this.taskId, State.PAUSED)
  //   .subscribe(updatedTask => {
  //     // console.log("Updated task", updatedTask);
  //     this.notifyChange(updatedTask);
  //   });
  // }

  // onComplete() {
  //   // console.log('Moving the task to STOP');
  //   this.taskService.changeTaskState(this.taskId, State.COMPLETED)
  //   .subscribe(updatedTask => {
  //     // console.log("Updated task", updatedTask);
  //     this.notifyChange(updatedTask);
  //   });
  // }

  // onCancel() {
  //   // console.log('Moving the task to CANCEL');
  //   this.taskService.changeTaskState(this.taskId, State.CANCELLED)
  //   .subscribe(updatedTask => {
  //     // console.log("Updated task", updatedTask);
  //     this.notifyChange(updatedTask);
  //   });
  // }

  // private notifyChange(task: TaskModel) {
  //   const notificationBody: TaskChange = {
  //     operation: TaskOperation.UPDATED,
  //     prevState: this.currentState,
  //     task: task
  //   };
  //   this.taskService.onTaskSaveUpdateChanged.next(notificationBody);
  // }

}
