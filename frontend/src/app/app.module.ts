import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AuthServices } from 'src/services/auth.service';
import { HttpAuthInterceptorProvider } from 'src/interceptors/http-auth.interceptor';
import { LocalStorageService } from 'src/services/local-storage.service';
import { UsersServices } from 'src/services/users.service';
import { PostsServices } from 'src/services/posts.services';

@NgModule({
  declarations: [AppComponent],
  entryComponents: [],
  imports: [
    BrowserModule,
    IonicModule.forRoot(),
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
    AuthServices,
    UsersServices,
    PostsServices,
    HttpAuthInterceptorProvider,
    LocalStorageService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
