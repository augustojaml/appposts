import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { PostsPageRoutingModule } from './posts-routing.module';

import { PostsPage } from './posts.page';
import { PostsServices } from 'src/services/posts.services';
import { DateAgoPipe } from 'src/pipe/date-ago-pipe';

@NgModule({
  imports: [CommonModule, FormsModule, IonicModule, PostsPageRoutingModule],
  declarations: [PostsPage, DateAgoPipe],
  providers: [PostsServices, DateAgoPipe],
})
export class PostsPageModule {}
