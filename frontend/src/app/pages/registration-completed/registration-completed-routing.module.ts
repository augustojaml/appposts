import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegistrationCompletedPage } from './registration-completed.page';

const routes: Routes = [
  {
    path: '',
    component: RegistrationCompletedPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RegistrationCompletedPageRoutingModule {}
