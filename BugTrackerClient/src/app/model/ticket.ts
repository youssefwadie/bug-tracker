export class Ticket {
  id: number;
  title: string;
  description: string;
  createdAt: Date;
  projectId: number;
  projectName: string;
  assignedDeveloper: string;
  type: TicketType;
  priority: TicketPriority;
  status: TicketStatus;
}

export enum TicketType {
  BUG_OR_ERROR = "Bugs/Errors",
  FEATURE_REQUESTS = "Feature Requests",
  TRAINING_OR_DOCUMENT_REQUEST = "Training/Document Requests",
}

export enum TicketStatus {
  NEW = "New",
  OPEN = "Open",
  IN_PROGRESS = "In Progress",
  RESOLVED = "Resolved",
}

export enum TicketPriority {
  LOW = "Low",
  MEDIUM = "Medium",
  HIGH = "High"
}
