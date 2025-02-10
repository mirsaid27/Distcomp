/* eslint-disable @typescript-eslint/unbound-method */
import { Test, TestingModule } from '@nestjs/testing';
import { StickerController } from './Sticker.controller';
import { StickerService } from './Sticker.service';
import { Response } from 'express';
import { HttpException, HttpStatus } from '@nestjs/common';
import { StickerResponseTo } from './Dto/StickerResponseTo';
import { StickerRequestTo, UpdateStickerTo } from './Dto/StickerRequestTo';
import { Sticker } from 'src/entities/Sticker';

describe('StickerController', () => {
  let controller: StickerController;
  let service: StickerService;

  const mockStickerService = {
    getAllStickers: jest.fn(),
    createSticker: jest.fn(),
    getStickerById: jest.fn(),
    deleteSticker: jest.fn(),
    updateSticker: jest.fn(),
  };

  const mockResponse = (): Partial<Response> => {
    return {
      status: jest.fn().mockReturnThis(),
      json: jest.fn().mockReturnThis(),
      send: jest.fn().mockReturnThis(),
    };
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [StickerController],
      providers: [
        {
          provide: StickerService,
          useValue: mockStickerService,
        },
      ],
    }).compile();

    controller = module.get<StickerController>(StickerController);
    service = module.get<StickerService>(StickerService);
  });

  describe('getAll', () => {
    it('should return an array of stickers', async () => {
      const result: Sticker[] = [{ id: 1, name: 'Test Sticker' }];
      jest.spyOn(service, 'getAllStickers').mockResolvedValue(result);

      const res = mockResponse() as Response;
      await controller.getAll(res);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.OK);
      expect(res.json).toHaveBeenCalledWith(expect.any(Array));
    });
  });

  describe('create', () => {
    it('should successfully create a sticker', async () => {
      const stickerRequest: StickerRequestTo = {
        name: 'New Sticker',
      };
      const stickerResponse: StickerResponseTo = {
        id: 1,
        name: 'New Sticker',
      };

      jest.spyOn(service, 'createSticker').mockResolvedValue(stickerResponse);

      const res = mockResponse() as Response;
      await controller.create(stickerRequest, res);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.CREATED);
      expect(res.json).toHaveBeenCalledWith(stickerResponse);
    });

    it('should throw an error if sticker with the same name already exists', async () => {
      const stickerRequest: StickerRequestTo = {
        name: 'Existing Sticker',
      };
      jest.spyOn(service, 'createSticker').mockImplementation(() => {
        throw new HttpException(
          {
            errorCode: 40005,
            errorMessage: 'Sticker with this name already exist.',
          },
          HttpStatus.BAD_REQUEST,
        );
      });

      const res = mockResponse() as Response;
      await expect(controller.create(stickerRequest, res)).rejects.toThrow(
        HttpException,
      );
    });
  });

  describe('getOne', () => {
    it('should return a sticker by ID', async () => {
      const stickerResponse: StickerResponseTo = {
        id: 1,
        name: 'Test Sticker' /* другие поля */,
      };
      jest.spyOn(service, 'getStickerById').mockResolvedValue(stickerResponse);

      const res = mockResponse() as Response;
      await controller.getOne(1, res);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.OK);
      expect(res.json).toHaveBeenCalledWith(stickerResponse);
    });

    it('should throw an error if sticker does not exist', async () => {
      jest.spyOn(service, 'getStickerById').mockImplementation(() => {
        throw new HttpException(
          { errorCode: 40006, errorMessage: 'Sticker does not exist.' },
          HttpStatus.BAD_REQUEST,
        );
      });

      const res = mockResponse() as Response;
      await expect(controller.getOne(1, res)).rejects.toThrow(HttpException);
    });
  });

  describe('delete', () => {
    it('should delete a sticker', async () => {
      jest.spyOn(service, 'deleteSticker').mockResolvedValue(undefined);

      const res = mockResponse() as Response;
      await controller.delete(1, res);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.NO_CONTENT);
      expect(res.send).toHaveBeenCalled();
    });

    it('should throw an error if sticker does not exist', async () => {
      jest.spyOn(service, 'deleteSticker').mockImplementation(() => {
        throw new HttpException(
          { errorCode: 40006, errorMessage: 'Sticker does not exist.' },
          HttpStatus.BAD_REQUEST,
        );
      });

      const res = mockResponse() as Response;
      await expect(controller.delete(1, res)).rejects.toThrow(HttpException);
    });
  });

  describe('update', () => {
    it('should successfully update a sticker', async () => {
      const stickerRequest: UpdateStickerTo = {
        id: 1,
        name: 'Updated Sticker' /* другие поля */,
      };
      const stickerResponse: StickerResponseTo = {
        id: 1,
        name: 'Updated Sticker' /* другие поля */,
      };

      jest.spyOn(service, 'updateSticker').mockResolvedValue(stickerResponse);

      const res = mockResponse() as Response;
      await controller.update(stickerRequest, res);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.OK);
      expect(res.json).toHaveBeenCalledWith(stickerResponse);
    });

    it('should throw an error if sticker does not exist', async () => {
      const stickerRequest: UpdateStickerTo = {
        id: 1,
        name: 'Non-Existent Sticker' /* другие поля */,
      };
      jest.spyOn(service, 'updateSticker').mockImplementation(() => {
        throw new HttpException(
          { errorCode: 40006, errorMessage: 'Sticker does not exist.' },
          HttpStatus.BAD_REQUEST,
        );
      });

      const res = mockResponse() as Response;
      await expect(controller.update(stickerRequest, res)).rejects.toThrow(
        HttpException,
      );
    });
  });
});
