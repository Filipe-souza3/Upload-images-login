import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindFileComponent } from './find-file.component';

describe('FindFileComponent', () => {
  let component: FindFileComponent;
  let fixture: ComponentFixture<FindFileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FindFileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FindFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
