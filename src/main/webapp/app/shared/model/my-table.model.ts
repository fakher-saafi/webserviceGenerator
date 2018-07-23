import { IWebservice } from 'app/shared/model//webservice.model';

export interface IMyTable {
    id?: number;
    tableName?: string;
    nbColomn?: number;
    webservice?: IWebservice;
}

export class MyTable implements IMyTable {
    constructor(public id?: number, public tableName?: string, public nbColomn?: number, public webservice?: IWebservice) {}
}
