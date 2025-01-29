import {
  ConflictException,
  Injectable,
  InternalServerErrorException,
  NotFoundException,
} from '@nestjs/common';
import { CollectionType, StorageService } from '../../storage/database';
import { Article } from 'src/entities/Article';
import { ArticleRequestTo, UpdateArticleTo } from './Dto/ArticleRequestTo';

@Injectable()
export class ArticleService {
  async getAllArticles(): Promise<ReadonlyArray<Article>> {
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
        throw new ConflictException();
      } else if (err instanceof NotFoundException) {
        throw new NotFoundException();
      }
      throw new InternalServerErrorException();
    }
  }

  async deleteArticle(id: number): Promise<void> {
    try {
      await StorageService.remove<Article>(CollectionType.ARTICLES, id);
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new ConflictException();
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
        throw new ConflictException();
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
        throw new ConflictException();
      }
      throw new InternalServerErrorException();
    }
  }
}
