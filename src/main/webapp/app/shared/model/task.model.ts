import { IWebservice } from 'app/shared/model//webservice.model';

export const enum TaskType {
    GET = 'GET',
    POST = 'POST',
    PUT = 'PUT',
    DELETE = 'DELETE'
}

export interface ITask {
    id?: number;
    taskType?: TaskType;
    description?: string;
    webservice?: IWebservice;
}

export class Task implements ITask {
    constructor(public id?: number, public taskType?: TaskType, public description?: string, public webservice?: IWebservice) {}
}
