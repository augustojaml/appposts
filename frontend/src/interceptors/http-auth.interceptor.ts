import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CONFIG } from 'src/config/api.config';
import { LocalStorageService } from 'src/services/local-storage.service';

@Injectable()
class HttpAuthInterceptor implements HttpInterceptor {
  constructor(private localStorageService: LocalStorageService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const localUser = this.localStorageService.getLocalUser();
    const baseUrlCharQuantity = API_CONFIG.baseURL.length;
    const requestToApi =
      req.url.substring(0, baseUrlCharQuantity) === API_CONFIG.baseURL;

    if (localUser && requestToApi) {
      const authRequest = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${localUser.token}`),
      });
      return next.handle(authRequest);
    }

    return next.handle(req);
  }
}

const HttpAuthInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: HttpAuthInterceptor,
  multi: true,
};
export { HttpAuthInterceptor, HttpAuthInterceptorProvider };
