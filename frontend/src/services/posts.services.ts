import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CONFIG } from 'src/config/api.config';
import { PostDTO } from 'src/dto/post.dto';
import { SendPostDTO } from 'src/dto/send-post.dto';

@Injectable()
class PostsServices {
  constructor(private httpClient: HttpClient) {}

  findAll(): Observable<PostDTO[]> {
    return this.httpClient.get<PostDTO[]>(`${API_CONFIG.baseURL}/posts`);
  }

  createPost(post: SendPostDTO): Observable<HttpResponse<string>> {
    const formData: FormData = new FormData();
    formData.set('title', post.title);
    formData.set('body', post.body);
    post.file && formData.set('file', post.file, post.fileName);

    return this.httpClient.post(`${API_CONFIG.baseURL}/posts`, formData, {
      observe: 'response',
      responseType: 'text',
    });
  }
}

export { PostsServices };
