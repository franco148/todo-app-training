import { Component, OnInit, Input } from '@angular/core';
import { TaskModel } from 'src/app/data/models/task.model';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { TaskService } from 'src/app/services/task.service';
import { TaskItemEditComponent } from '../task-item-edit/task-item-edit.component';
import { TaskItemOptionsComponent } from '../task-item-options/task-item-options.component';
import { Priority } from 'src/app/data/enums/priority.enum';

@Component({
  selector: 'app-task-list-item',
  templateUrl: './task-list-item.component.html',
  styleUrls: ['./task-list-item.component.css']
})
export class TaskListItemComponent implements OnInit {

  @Input() taskItem: TaskModel;

  itemPriority = 'red';

  dialogOptions: MatDialogRef<TaskItemOptionsComponent, any>;

  constructor(private dialog: MatDialog, private taskService: TaskService) { }

  ngOnInit(): void {
  }

  moreOptionMouseEnter() {
    console.log('Mouse entered ...');
    // this.dialogOptions = this.dialog.open(TaskItemOptionsComponent, {});
  }

  moreOptionsMouseLeave() {
    console.log('Mouse leave ...');
    this.dialogOptions.close();
  }

  addTaskFullInformation() {
    console.log('Full task information clicked...');
    const dialogRef = this.dialog.open(TaskItemEditComponent, {
      data: {
        taskItem: this.taskItem
      },
      width: '330px',
      disableClose: false
    });

    dialogRef.afterClosed().subscribe(taskResult => console.log(taskResult));
  }

  getItemPriority() {
    let priorityColor: string;

    switch (this.taskItem?.priority) {
      case Priority.HIGH:
        priorityColor = 'red';
        break;
      case Priority.MEDIUM:
        priorityColor = 'yellow';
        break;
      case Priority.LOW:
        priorityColor = 'green';
        break;
      default:
        priorityColor = 'gray';
        break;
    }

    return priorityColor;
  }

}
