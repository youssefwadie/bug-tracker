import {User} from "./user";
import {Ticket} from "./ticket";

export class Project {
  id: number;
  name: string;
  description: string;
  teamMembers: Array<User>;
  tickets: Array<Ticket>;
}
