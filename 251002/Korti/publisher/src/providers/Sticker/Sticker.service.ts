import {
  ConflictException,
  HttpException,
  HttpStatus,
  Injectable,
  InternalServerErrorException,
} from '@nestjs/common';
import { Sticker } from 'src/entities/Sticker';
import { CollectionType, StorageService } from '../../storage/database';
import { StickerRequestTo, UpdateStickerTo } from './Dto/StickerRequestTo';

@Injectable()
export class StickerService {
  async getAllStickers(): Promise<ReadonlyArray<Sticker>> {
    return await StorageService.getAll<Sticker>(CollectionType.STICKERS);
  }

  async createSticker(sticker: StickerRequestTo): Promise<Sticker> {
    try {
      const id = await StorageService.generateId(CollectionType.STICKERS);
      const item: Sticker = {
        id: id,
        name: sticker.name,
      };
      const createdSticker = await StorageService.add<Sticker>(
        CollectionType.STICKERS,
        item,
      );
      return createdSticker;
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40005,
            errorMessage: 'Sticker with this name already exist.',
          },
          HttpStatus.BAD_REQUEST,
        );
      }
      throw InternalServerErrorException;
    }
  }

  async deleteSticker(id: number): Promise<void> {
    try {
      await StorageService.remove<Sticker>(CollectionType.STICKERS, id);
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40006,
            errorMessage: 'Sticker does not exist.',
          },
          HttpStatus.BAD_REQUEST,
        );
      }
      throw new InternalServerErrorException();
    }
  }

  async getStickerById(id: number): Promise<Sticker> {
    try {
      const sticker = await StorageService.getById<Sticker>(
        CollectionType.STICKERS,
        id,
      );
      return sticker;
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40006,
            errorMessage: 'Sticker does not exist.',
          },
          HttpStatus.BAD_REQUEST,
        );
      }
      throw new InternalServerErrorException();
    }
  }

  async updateSticker(item: UpdateStickerTo): Promise<Sticker> {
    try {
      const sticker = {
        ...item,
      };
      await StorageService.update<Sticker>(CollectionType.STICKERS, sticker);
      return sticker;
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40006,
            errorMessage: 'Sticker does not exist.',
          },
          HttpStatus.BAD_REQUEST,
        );
      }
      throw new InternalServerErrorException();
    }
  }
}
