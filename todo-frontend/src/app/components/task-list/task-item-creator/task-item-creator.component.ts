import { Component, OnInit } from '@angular/core';
import { TaskService } from '../../../services/task.service';
import { TaskModel } from 'src/app/data/models/task.model';
import { UserService } from 'src/app/services/user.service';
import { TaskChange } from 'src/app/data/interfaces/task-change';
import { TaskOperation } from 'src/app/data/enums/task-operation.enum';
import { State } from 'src/app/data/enums/state.enum';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-task-item-creator',
  templateUrl: './task-item-creator.component.html',
  styleUrls: ['./task-item-creator.component.css']
})
export class TaskItemCreatorComponent implements OnInit {

  constructor(private userService: UserService,
    private taskService: TaskService,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  // onSaveTask(textInputValue: string) {
  //   console.log('Enter key pressed...', textInputValue);

  //   const taskStartTime = new Date();
  //   const taskEndTime = new Date();
  //   taskEndTime.setHours(23, 59, 59, 999);

  //   const task = {
  //     title: textInputValue,
  //     startTime: taskStartTime,
  //     endTime: taskEndTime
  //   };

  //   const userId = this.userService.getLoggedUser().id;
  //   this.taskService.addTask(userId, task).subscribe(savedTask => {
  //     // console.log('Saved Task: ', savedTask);
  //     const notificationBody: TaskChange = {
  //       operation: TaskOperation.SAVED,
  //       prevState: State.PLANNED,
  //       task: savedTask
  //     };
  //     this.taskService.onTaskSaveUpdateChanged.next(notificationBody);
  //   }, saveError => {
  //     const errorMessage = 'Error ocurred while saving a task: Status=' + saveError.status;
  //     console.log(errorMessage, saveError);
  //     this.snackBar.open(errorMessage, 'ERROR', { duration: 5000 });
  //   });
  // }
}
