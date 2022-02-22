import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RegistrationCompletedPageRoutingModule } from './registration-completed-routing.module';

import { RegistrationCompletedPage } from './registration-completed.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RegistrationCompletedPageRoutingModule
  ],
  declarations: [RegistrationCompletedPage]
})
export class RegistrationCompletedPageModule {}
