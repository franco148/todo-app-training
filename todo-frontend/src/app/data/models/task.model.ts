export class TaskModel {

  id: number;
	title: string;
	description: string;
	startTime: Date;
	endTime: Date;
	duration: number;
	isDeleted: boolean;
	state: string;
	priority: string;
}
