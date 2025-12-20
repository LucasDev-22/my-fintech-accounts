export interface Transaction {
  id?: number;
  icon: string;
  loja: string;
  valor: number;
  data: string | Date;
}

export interface Account {
  id: number;
  owner: string;
  balance: number;
}
