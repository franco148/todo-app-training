import { Component, OnInit, Inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { TaskModel } from 'src/app/data/models/task.model';
import { DatePipe, formatDate } from '@angular/common';

@Component({
  selector: 'app-task-item-edit',
  templateUrl: './task-item-edit.component.html',
  styleUrls: ['./task-item-edit.component.css']
})
export class TaskItemEditComponent implements OnInit {

  taskItem: TaskModel;
  startTime: string;

  constructor(@Inject(MAT_DIALOG_DATA) public dataParam: any,
              private dialogRef: MatDialogRef<TaskItemEditComponent>) { }

  ngOnInit(): void {
    this.taskItem = this.dataParam.taskItem;
    // this.startTime = this.taskItem.startTime.getHours() + ':' + this.taskItem.startTime.getMinutes();
    // const pipe = new DatePipe("en-US");
    // this.startTime = formatDate(this.taskItem.startTime, 'h:mm a', 'en-US');
    console.log('AAAAAAAAAAAAAAAA', this.startTime);
  }

  onSave(form: NgForm) {
    console.log('Task has been saved...', this.taskItem);
    // console.log('For start time: ', formatDate(this.startTime, 'MM/DD/YYYY hh:mm A', 'en-US'));
    // const todayStartTime = new Date();
    this.dialogRef.close();
  }

  onCancel() {
    this.dialogRef.close();
  }
}
