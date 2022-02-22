import { Injectable } from '@angular/core';
import { STORAGE_KEYS } from 'src/config/storage-key.config';
import { LocalUserDTO } from 'src/dto/local-user.dto';

@Injectable()
class LocalStorageService {
  getLocalUser(): LocalUserDTO {
    const user = localStorage.getItem(STORAGE_KEYS.localUser);
    if (user === null) {
      return;
    }
    return JSON.parse(user);
  }

  setLocalUser(object: LocalUserDTO) {
    if (object === null) {
      localStorage.removeItem(STORAGE_KEYS.localUser);
      return;
    }
    localStorage.setItem(STORAGE_KEYS.localUser, JSON.stringify(object));
  }
}

export { LocalStorageService };
