export interface IBook {
    id?: number;
    name?: string;
}

export class Book implements IBook {
    constructor(public id?: number, public name?: string) {}
}
