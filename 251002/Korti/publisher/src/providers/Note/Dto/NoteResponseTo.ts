import { Expose } from 'class-transformer';
import { IsInt, IsString, MaxLength, MinLength } from 'class-validator';

export class NoteResponseTo {
  @IsInt()
  @Expose()
  id: number;

  @IsInt()
  @Expose()
  articleId: number;

  @IsString()
  @MinLength(2)
  @MaxLength(2048)
  @Expose()
  content: string;
}
