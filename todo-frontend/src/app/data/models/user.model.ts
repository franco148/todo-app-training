import { TaskModel } from './task.model';

export class UserModel {

  id: number;
	firstName: string;
	lastName: string;
	nickName: string;
	email: string;
	isLogged: boolean;
	isEnabled: boolean;
	isDeleted: boolean;
  tasks: TaskModel[];
}
