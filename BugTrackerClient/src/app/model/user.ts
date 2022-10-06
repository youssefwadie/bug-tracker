export class User {
  id: number;
  fullName: string = '';
  email: string = '';
  role: Role;
}

export class UserLogin {
  email: string;
  password: string;
}

export enum Role {
  ROLE_ADMIN = "Admin",
  ROLE_DEVELOPER = "Developer",
}
