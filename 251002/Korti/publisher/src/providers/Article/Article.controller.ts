import {
  Body,
  ConflictException,
  Controller,
  Delete,
  Get,
  HttpException,
  HttpStatus,
  NotFoundException,
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
    const responseData: ArticleResponseTo[] = articles.map((el) => {
      return {
        ...el,
        editorId: Number(el.editorId),
        id: Number(el.id),
      };
    });
    res.status(HttpStatus.OK).json(
      plainToInstance(ArticleResponseTo, responseData, {
        excludeExtraneousValues: true,
      }),
    );
  }

  @Post()
  async create(@Body() body: ArticleRequestTo, @Res() res: Response) {
    try {
      const article: Article = await this.articleService.createArticle(body);
      res
        .status(HttpStatus.CREATED)
        .json(plainToInstance(ArticleResponseTo, article));
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40204,
            errorMessage: 'Article already exist.',
          },
          HttpStatus.BAD_REQUEST,
        );
      } else if (err instanceof NotFoundException) {
        throw new HttpException(
          {
            errorCode: 40400,
            errorMessage: 'Editor does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
      throw err;
    }
  }

  @Get(':id')
  async getOne(
    @Param('id', ParseIntPipe) idArticle: number,
    @Res() res: Response,
  ) {
    try {
      const article = await this.articleService.getArticleById(idArticle);
      const responseData: ArticleResponseTo = {
        ...article,
        id: Number(article.id),
        editorId: Number(article.editorId),
      };
      res.status(HttpStatus.OK).json(
        plainToInstance(ArticleResponseTo, responseData, {
          excludeExtraneousValues: true,
        }),
      );
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40403,
            errorMessage: 'Article does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
      throw err;
    }
  }

  @Delete(':id')
  async deleteOne(
    @Param('id', ParseIntPipe) idArticle: number,
    @Res() res: Response,
  ) {
    try {
      await this.articleService.deleteArticle(idArticle);
      res.status(HttpStatus.NO_CONTENT).send();
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40403,
            errorMessage: 'Article does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
      throw err;
    }
  }

  @Put()
  async update(@Body() req: UpdateArticleTo, @Res() res: Response) {
    try {
      console.log(req);
      const article: Article = await this.articleService.updateArticle(req);
      const responseData: ArticleResponseTo = {
        ...article,
        editorId: Number(article.editorId),
        id: Number(article.id),
      };
      res.status(HttpStatus.OK).json(
        plainToInstance(ArticleResponseTo, responseData, {
          excludeExtraneousValues: true,
        }),
      );
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40403,
            errorMessage: 'Article does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
      throw err;
    }
  }
}
