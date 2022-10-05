export abstract class AppConstants {

  public static get LOCALES_ARGUMENT(): Intl.LocalesArgument {
    return 'en-US'
  };

  public static get DATE_TIME_FORMAT_OPTIONS(): Intl.DateTimeFormatOptions {
    return {
      month: 'numeric',
      day: 'numeric',
      hour: 'numeric',
      minute: 'numeric'
    }
  }

  public static get TOTAL_COUNT_HEADER_NAME(): string {
    return "X-Total-Count";
  }
}
