import { IsInt, IsString, MaxLength, MinLength } from 'class-validator';
import { Expose } from 'class-transformer';

export class ArticleResponseTo {
  @Expose()
  @IsInt()
  id: number;

  @Expose()
  @IsInt()
  editorId: number;

  @Expose()
  @IsString()
  @MinLength(2)
  @MaxLength(64)
  title: string;

  @Expose()
  @IsString()
  @MinLength(4)
  @MaxLength(2048)
  content: string;

  @Expose()
  @IsString()
  created: string;

  @Expose()
  modified: string | null;
}
