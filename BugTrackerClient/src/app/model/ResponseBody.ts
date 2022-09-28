export interface SimpleResponse {
  error: string;
  message: string;
  status: number;
  timestamp: Date
}

export interface InvalidDataResponse {
  error: string;
  invalidData: object;
  status: number;
  timestamp: Date
}
