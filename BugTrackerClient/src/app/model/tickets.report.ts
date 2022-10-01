export class TicketsReport {
  status = new Array<ReportEntry>();
  type = new Array<ReportEntry>();
  priority = new Array<ReportEntry>();
}

export class ReportEntry {
  name: string;
  value: number;
}
