import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PixModal } from './pix-modal';

describe('PixModal', () => {
  let component: PixModal;
  let fixture: ComponentFixture<PixModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PixModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PixModal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
