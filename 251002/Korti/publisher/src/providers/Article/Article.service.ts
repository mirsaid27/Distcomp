import {
  ConflictException,
  HttpException,
  HttpStatus,
  Injectable,
  InternalServerErrorException,
  NotFoundException,
} from '@nestjs/common';
import { CollectionType, StorageService } from '../../storage/database';
import { Article } from 'src/entities/Article';
import { ArticleRequestTo, UpdateArticleTo } from './Dto/ArticleRequestTo';
import { ArticleResponseTo } from './Dto/ArticleResponseTo';

@Injectable()
export class ArticleService {
  async getAllArticles(): Promise<ReadonlyArray<ArticleResponseTo>> {
    return await StorageService.getAll<Article>(CollectionType.ARTICLES);
  }

  async createArticle(article: ArticleRequestTo): Promise<Article> {
    try {
      const date = new Date().toISOString();
      const articleId: number = await StorageService.generateId(
        CollectionType.ARTICLES,
      );
      const articleToAdd: Article = {
        ...article,
        created: date,
        modified: null,
        id: articleId,
      };

      const newArticle = await StorageService.add<Article>(
        CollectionType.ARTICLES,
        articleToAdd,
      );
      return newArticle;
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
      throw new InternalServerErrorException();
    }
  }

  async deleteArticle(id: number): Promise<void> {
    try {
      await StorageService.remove<Article>(CollectionType.ARTICLES, id);
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
      throw new InternalServerErrorException();
    }
  }

  async getArticleById(id: number): Promise<Article> {
    try {
      const article = await StorageService.getById<Article>(
        CollectionType.ARTICLES,
        id,
      );
      return article;
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
      throw new InternalServerErrorException();
    }
  }

  async updateArticle(body: UpdateArticleTo): Promise<Article> {
    try {
      const modDate = new Date().toISOString();
      const article = await StorageService.getById<Article>(
        CollectionType.ARTICLES,
        body.id,
      );
      const modArticle: Article = {
        ...article,
        modified: modDate,
      };
      await StorageService.update<Article>(CollectionType.ARTICLES, modArticle);
      return modArticle;
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
      throw new InternalServerErrorException();
    }
  }
}
