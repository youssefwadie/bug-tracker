import { TestBed } from '@angular/core/testing';

import { LoginRouteGuard } from './login.route.guard';

describe('LoginRouteGuard', () => {
  let guard: LoginRouteGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(LoginRouteGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
