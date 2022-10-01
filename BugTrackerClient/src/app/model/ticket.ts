export class Ticket {
  id: number;
  title: string;
  description: string;
  createdAt: Date;
  projectId: number;
  projectName: string;
  assignedDeveloper: string;
  type: string;
  priority: string;
  status: string;
}
