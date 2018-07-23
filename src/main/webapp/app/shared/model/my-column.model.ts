import { IMyTable } from 'app/shared/model//my-table.model';

export interface IMyColumn {
    id?: number;
    columnName?: string;
    type?: string;
    table?: IMyTable;
}

export class MyColumn implements IMyColumn {
    constructor(public id?: number, public columnName?: string, public type?: string, public table?: IMyTable) {}
}
