import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { CredentialDTO } from 'src/dto/credential.dto';
import { API_CONFIG } from 'src/config/api.config';
import { Observable } from 'rxjs';
import { LocalUserDTO } from 'src/dto/local-user.dto';
import { JwtHelperService } from '@auth0/angular-jwt';
import { LocalStorageService } from './local-storage.service';

@Injectable()
class AuthServices {
  jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService
  ) {}

  login(credential: CredentialDTO): Observable<HttpResponse<string>> {
    return this.httpClient.post(`${API_CONFIG.baseURL}/login`, credential, {
      observe: 'response',
      responseType: 'text',
    });
  }

  setLocalUser(bearerToken: string) {
    const user: LocalUserDTO = {
      token: bearerToken.substring(7),
      email: this.jwtHelper.decodeToken(bearerToken.substring(7)).sub,
    };
    this.localStorageService.setLocalUser(user);
  }

  findMe() {
    return this.httpClient.get(`${API_CONFIG.baseURL}/auth/me`);
  }

  logout() {
    this.localStorageService.setLocalUser(null);
  }
}

export { AuthServices };
