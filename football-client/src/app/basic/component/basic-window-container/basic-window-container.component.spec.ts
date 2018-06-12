import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BasicWindowContainerComponent } from './basic-window-container.component';

describe('BasicWindowContainerComponent', () => {
  let component: BasicWindowContainerComponent;
  let fixture: ComponentFixture<BasicWindowContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BasicWindowContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BasicWindowContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
