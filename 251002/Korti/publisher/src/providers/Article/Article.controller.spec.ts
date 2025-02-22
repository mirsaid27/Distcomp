/* eslint-disable @typescript-eslint/unbound-method */
import { Test, TestingModule } from '@nestjs/testing';
import { ArticleController } from './Article.controller';
import { ArticleService } from './Article.service';
import { ArticleResponseTo } from './Dto/ArticleResponseTo';
import { ArticleRequestTo, UpdateArticleTo } from './Dto/ArticleRequestTo';
import { HttpException, HttpStatus } from '@nestjs/common';
import { Response } from 'express';

describe('ArticleController', () => {
  let controller: ArticleController;
  let service: ArticleService;

  const mockArticleService = {
    getAllArticles: jest.fn(),
    createArticle: jest.fn(),
    getArticleById: jest.fn(),
    deleteArticle: jest.fn(),
    updateArticle: jest.fn(),
  };

  const mockResponse = (): Partial<Response> => {
    return {
      status: jest.fn().mockReturnThis(),
      json: jest.fn().mockReturnThis(),
      send: jest.fn().mockReturnThis(),
      sendStatus: jest.fn().mockReturnThis(),
    };
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [ArticleController],
      providers: [
        {
          provide: ArticleService,
          useValue: mockArticleService,
        },
      ],
    }).compile();

    controller = module.get<ArticleController>(ArticleController);
    service = module.get<ArticleService>(ArticleService);
  });

  describe('getAll', () => {
    it('should return an array of articles', async () => {
      const result: ArticleResponseTo[] = [
        {
          id: 1,
          editorId: 1,
          title: 'Test Article',
          content: 'Content',
          created: '',
          modified: null,
        },
      ];
      jest.spyOn(service, 'getAllArticles').mockResolvedValue(result);

      const res = mockResponse() as Response;
      await controller.getAll(res);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.OK);
      expect(res.json).toHaveBeenCalledWith(result);
    });
  });

  describe('create', () => {
    it('should successfully create an article', async () => {
      const articleRequest: ArticleRequestTo = {
        title: 'New Article',
        content: 'Content',
        editorId: 1,
      };
      const articleResponse: ArticleResponseTo = {
        id: 1,
        editorId: 1,
        title: 'New Article',
        content: 'Content',
        created: '',
        modified: null,
      };

      jest.spyOn(service, 'createArticle').mockResolvedValue(articleResponse);

      const res = mockResponse() as Response;
      await controller.create(articleRequest, res);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.CREATED);
      expect(res.json).toHaveBeenCalledWith(articleResponse);
    });

    it('should throw an error if article already exists', async () => {
      const articleRequest: ArticleRequestTo = {
        title: 'Existing Article',
        content: 'Content',
        editorId: 1,
      };
      jest.spyOn(service, 'createArticle').mockImplementation(() => {
        throw new HttpException(
          {
            errorCode: 40204,
            errorMessage: 'Article already exists.',
          },
          HttpStatus.BAD_REQUEST,
        );
      });

      const res = mockResponse() as Response;
      await expect(controller.create(articleRequest, res)).rejects.toThrow(
        HttpException,
      );
    });
  });

  describe('getOne', () => {
    it('should return an article by ID', async () => {
      const articleResponse: ArticleResponseTo = {
        id: 1,
        editorId: 1,
        title: 'Test Article',
        content: 'Content',
        created: '',
        modified: null,
      };
      jest.spyOn(service, 'getArticleById').mockResolvedValue(articleResponse);

      const res = mockResponse() as Response;
      await controller.getOne(1, res);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.OK);
      expect(res.json).toHaveBeenCalledWith(articleResponse);
    });

    it('should throw an error if article does not exist', async () => {
      jest.spyOn(service, 'getArticleById').mockImplementation(() => {
        throw new HttpException(
          {
            errorCode: 40403,
            errorMessage: 'Article does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      });

      const res = mockResponse() as Response;
      await expect(controller.getOne(1, res)).rejects.toThrow(HttpException);
    });
  });

  describe('deleteOne', () => {
    it('should delete an article', async () => {
      jest.spyOn(service, 'deleteArticle').mockResolvedValue(undefined);

      const res = mockResponse() as Response;
      await controller.deleteOne(1, res);
      expect(res.send).toHaveBeenCalled();
      expect(res.status).toHaveBeenCalledWith(HttpStatus.NO_CONTENT);
    });

    it('should throw an error if article does not exist', async () => {
      jest.spyOn(service, 'deleteArticle').mockImplementation(() => {
        throw new HttpException(
          {
            errorCode: 40403,
            errorMessage: 'Article does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      });

      const res = mockResponse() as Response;
      await expect(controller.deleteOne(1, res)).rejects.toThrow(HttpException);
    });
  });

  describe('update', () => {
    it('should successfully update an article', async () => {
      const articleRequest: UpdateArticleTo = {
        id: 1,
        title: 'Updated Article',
        content: 'Updated Content',
        editorId: 1,
      };
      const articleResponse: ArticleResponseTo = {
        id: 1,
        editorId: 1,
        title: 'Updated Article',
        content: 'Updated Content',
        created: '',
        modified: null,
      };

      jest.spyOn(service, 'updateArticle').mockResolvedValue(articleResponse);

      const res = mockResponse() as Response;
      await controller.update(articleRequest, res);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.OK);
      expect(res.json).toHaveBeenCalledWith(articleResponse);
    });

    it('should throw an error if article does not exist', async () => {
      const articleRequest: UpdateArticleTo = {
        id: 1,
        title: 'Non-Existent Article',
        content: 'Content',
        editorId: 1,
      };
      jest.spyOn(service, 'updateArticle').mockImplementation(() => {
        throw new HttpException(
          {
            errorCode: 40403,
            errorMessage: 'Article does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      });

      const res = mockResponse() as Response;
      await expect(controller.update(articleRequest, res)).rejects.toThrow(
        HttpException,
      );
    });
  });
});
