import {
  ConflictException,
  HttpException,
  HttpStatus,
  Injectable,
  InternalServerErrorException,
} from '@nestjs/common';
import { Sticker } from 'src/entities/Sticker';
import { StickerRequestTo, UpdateStickerTo } from './Dto/StickerRequestTo';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

@Injectable()
export class StickerService {
  constructor(
    @InjectRepository(Sticker)
    private readonly stickerRepository: Repository<Sticker>,
  ) {}

  async getAllStickers(): Promise<ReadonlyArray<Sticker>> {
    return await this.stickerRepository.find();
  }

  async createSticker(sticker: StickerRequestTo): Promise<Sticker> {
    try {
      const st = this.stickerRepository.create(sticker);
      return await this.stickerRepository.save(st);
    } catch (err) {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      if ((err.code as string) === '23505') {
        throw new HttpException(
          {
            errorCode: 40005,
            errorMessage: 'Sticker with this name already exist.',
          },
          HttpStatus.FORBIDDEN,
        );
      }
      throw InternalServerErrorException;
    }
  }

  async deleteSticker(id: number): Promise<void> {
    try {
      const sticker = await this.stickerRepository.findOne({ where: { id } });
      if (!sticker) throw new ConflictException();
      await this.stickerRepository.delete(id);
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40006,
            errorMessage: 'Sticker does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
      throw new InternalServerErrorException();
    }
  }

  async getStickerById(id: number): Promise<Sticker> {
    try {
      const sticker = await this.stickerRepository.findOne({ where: { id } });
      if (!sticker) throw new ConflictException();
      return sticker;
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40006,
            errorMessage: 'Sticker does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
      throw new InternalServerErrorException();
    }
  }

  async updateSticker(item: UpdateStickerTo): Promise<Sticker> {
    try {
      const st = await this.stickerRepository.findOne({
        where: { id: item.id },
      });
      if (!st) throw new ConflictException();
      await this.stickerRepository.update(item.id, {
        name: item.name,
      });
      const updSticker = await this.stickerRepository.findOne({
        where: { id: item.id },
      });
      if (!updSticker) throw new Error();
      return updSticker;
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
