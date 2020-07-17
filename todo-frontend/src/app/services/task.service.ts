import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Subject } from 'rxjs';

import { SERVER_END_POINT_URL } from '../constants/app.constant';
import { TaskChange } from '../data/interfaces/task-change';
import { TaskModel } from '../data/models/task.model';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  serverUrl = SERVER_END_POINT_URL;
  onTaskSaveUpdateChanged = new Subject<TaskChange>();

  constructor(private http: HttpClient) { }


  addTask(userId: number, task: any) {
    return this.http.post<TaskModel>(`${this.serverUrl}/users/${userId}/task`, task);
  }

  getTodaysUserTasks(userId: number) {
    const todayDate = new Date();
    const offset = todayDate.getTimezoneOffset()
    const today = new Date(todayDate.getTime() - (offset*60*1000)).toISOString().split('T')[0];

    return this.http.get<TaskModel[]>(`${this.serverUrl}/users/${userId}/task/filteredby?startDate=${today}&endDate=${today}`);
  }

  changeTaskState(taskId: number, taskState: string) {
    return this.http.patch<TaskModel>(`${this.serverUrl}/tasks/${taskId}?state=${taskState}`, {});
  }
}
