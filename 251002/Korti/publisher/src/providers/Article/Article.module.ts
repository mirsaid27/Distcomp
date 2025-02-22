import { Module } from '@nestjs/common';
import { ArticleController } from './Article.controller';
import { ArticleService } from './Article.service';

@Module({
  imports: [],
  providers: [ArticleService],
  controllers: [ArticleController],
})
export class ArticleModule {}
