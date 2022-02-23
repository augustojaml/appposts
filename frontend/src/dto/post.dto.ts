import { AuthorDTO } from './author.dto';

interface PostDTO {
  id: string;
  title: string;
  body: string;
  date: Date;
  image: string;
  authorPost: AuthorDTO;
}

export { PostDTO };
