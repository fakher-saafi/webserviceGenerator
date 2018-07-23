import { IUser } from 'app/core/user/user.model';

export const enum DataType {
    FILE = 'FILE',
    DATABASE = 'DATABASE',
    CREATE = 'CREATE'
}

export const enum DatabaseType {
    SQL = 'SQL',
    MONGODB = 'MONGODB',
    CASSANDRA = 'CASSANDRA'
}

export const enum SqlProduct {
    MYSQL = 'MYSQL',
    POSTGRESQL = 'POSTGRESQL',
    ORACLE = 'ORACLE',
    MSSQL = 'MSSQL'
}

export interface IWebservice {
    id?: number;
    webserviceName?: string;
    description?: string;
    datatype?: DataType;
    databaseType?: DatabaseType;
    databaseProduct?: SqlProduct;
    databasePath?: string;
    dbUsername?: string;
    dbPass?: string;
    user?: IUser;
}

export class Webservice implements IWebservice {
    constructor(
        public id?: number,
        public webserviceName?: string,
        public description?: string,
        public datatype?: DataType,
        public databaseType?: DatabaseType,
        public databaseProduct?: SqlProduct,
        public databasePath?: string,
        public dbUsername?: string,
        public dbPass?: string,
        public user?: IUser
    ) {}
}
