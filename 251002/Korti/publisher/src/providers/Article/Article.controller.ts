import {
  Body,
  Controller,
  Delete,
  Get,
  HttpStatus,
  Param,
  ParseIntPipe,
  Post,
  Put,
  Res,
} from '@nestjs/common';
import { ArticleService } from './Article.service';
import { Response } from 'express';
import { ArticleResponseTo } from './Dto/ArticleResponseTo';
import { plainToInstance } from 'class-transformer';
import { ArticleRequestTo, UpdateArticleTo } from './Dto/ArticleRequestTo';
import { Article } from 'src/entities/Article';

@Controller('api/v1.0/articles')
export class ArticleController {
  constructor(private readonly articleService: ArticleService) {}

  @Get()
  async getAll(@Res() res: Response) {
    const articles = await this.articleService.getAllArticles();
    res.status(HttpStatus.OK).json(
      plainToInstance(ArticleResponseTo, articles, {
        excludeExtraneousValues: true,
      }),
    );
  }

  @Post()
  async create(@Body() body: ArticleRequestTo, @Res() res: Response) {
    const article: Article = await this.articleService.createArticle(body);
    res
      .status(HttpStatus.CREATED)
      .json(plainToInstance(ArticleResponseTo, article));
  }

  @Get(':id')
  async getOne(
    @Param('id', ParseIntPipe) idArticle: number,
    @Res() res: Response,
  ) {
    const article = await this.articleService.getArticleById(idArticle);
    res.status(HttpStatus.OK).json(
      plainToInstance(ArticleResponseTo, article, {
        excludeExtraneousValues: true,
      }),
    );
  }

  @Delete(':id')
  async deleteOne(
    @Param('id', ParseIntPipe) idArticle: number,
    @Res() res: Response,
  ) {
    await this.articleService.deleteArticle(idArticle);
    res.status(HttpStatus.NO_CONTENT).send();
  }

  @Put()
  async update(@Body() req: UpdateArticleTo, @Res() res: Response) {
    const article: Article = await this.articleService.updateArticle(req);
    res.status(HttpStatus.OK).json(
      plainToInstance(ArticleResponseTo, article, {
        excludeExtraneousValues: true,
      }),
    );
  }
}
