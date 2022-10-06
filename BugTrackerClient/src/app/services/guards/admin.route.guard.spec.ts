import { TestBed } from '@angular/core/testing';

import { AdminRouteGuard } from './admin.route.guard.service';

describe('AdminRouteGuard', () => {
  let guard: AdminRouteGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(AdminRouteGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
