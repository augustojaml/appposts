import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CONFIG } from 'src/config/api.config';
import { ProfileDTO } from 'src/dto/profile.dto';
import { CreateUserDTO } from 'src/dto/user.dto';

@Injectable()
class UsersServices {
  constructor(private httpClient: HttpClient) {}

  createUser(createUser: CreateUserDTO): Observable<HttpResponse<string>> {
    const formData: FormData = new FormData();
    formData.set('name', createUser.name);
    formData.set('email', createUser.email);
    formData.set('password', createUser.password);

    return this.httpClient.post(`${API_CONFIG.baseURL}/users`, formData, {
      observe: 'response',
      responseType: 'text',
    });
  }

  updateUser(profileDTO: ProfileDTO): Observable<any> {
    const formData: FormData = new FormData();
    formData.set('name', profileDTO.name);
    formData.set('email', profileDTO.email);

    profileDTO.file && formData.set('file', profileDTO.file, 'file1.jpg');

    return this.httpClient.put(
      `${API_CONFIG.baseURL}/users/${profileDTO.id}`,
      formData,
      {
        observe: 'response',
        responseType: 'text',
        headers: {
          ContentType: 'multipart/form-data',
        },
      }
    );
  }
}

export { UsersServices };
