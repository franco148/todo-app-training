import { TaskOperation } from '../enums/task-operation.enum';
import { State } from '../enums/state.enum';
import { TaskModel } from '../models/task.model';

export interface TaskChange {
  operation: TaskOperation;
  prevState: State;
  task: TaskModel;
}
