export class User {
  id: number;
  fullName: string;
  email: string;
  role: string;
}

export class UserLogin {
  email: string;
  password: string;
}

export class UserSelection {
  id: number;
  name: string;
}

export interface UserRole {
  role: string;
}
