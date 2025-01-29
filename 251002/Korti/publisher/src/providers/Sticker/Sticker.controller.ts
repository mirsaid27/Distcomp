import {
  Body,
  ConflictException,
  Controller,
  Delete,
  Get,
  HttpException,
  HttpStatus,
  Param,
  ParseIntPipe,
  Post,
  Put,
  Res,
} from '@nestjs/common';
import { StickerService } from './Sticker.service';
import { Response } from 'express';
import { plainToInstance } from 'class-transformer';
import { StickerResponseTo } from './Dto/StickerResponseTo';
import { Sticker } from 'src/entities/Sticker';
import { StickerRequestTo, UpdateStickerTo } from './Dto/StickerRequestTo';

@Controller('api/v1.0/stickers')
export class StickerController {
  constructor(private readonly stickerService: StickerService) {}

  @Get()
  async getAll(@Res() res: Response) {
    const stickers: ReadonlyArray<Sticker> =
      await this.stickerService.getAllStickers();
    const responseData: StickerResponseTo[] = stickers.map((el) => {
      return {
        ...el,
        id: Number(el.id),
      };
    });
    res.status(HttpStatus.OK).json(
      plainToInstance(StickerResponseTo, responseData, {
        excludeExtraneousValues: true,
      }),
    );
  }

  @Post()
  async create(@Body() body: StickerRequestTo, @Res() res: Response) {
    try {
      const sticker = await this.stickerService.createSticker(body);
      const responseData = {
        ...sticker,
        id: Number(sticker.id),
      };
      res.status(HttpStatus.CREATED).json(
        plainToInstance(StickerResponseTo, responseData, {
          excludeExtraneousValues: true,
        }),
      );
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
      throw err;
    }
  }

  @Delete(':id')
  async delete(
    @Param('id', ParseIntPipe) stickerId: number,
    @Res() res: Response,
  ) {
    try {
      await this.stickerService.deleteSticker(stickerId);
      res.status(HttpStatus.NO_CONTENT).send();
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
      throw err;
    }
  }

  @Get(':id')
  async getOne(
    @Param('id', ParseIntPipe) stickerId: number,
    @Res() res: Response,
  ) {
    try {
      const sticker = await this.stickerService.getStickerById(stickerId);
      const responseData: StickerResponseTo = {
        ...sticker,
        id: Number(sticker.id),
      };
      res.status(HttpStatus.OK).json(
        plainToInstance(StickerResponseTo, responseData, {
          excludeExtraneousValues: true,
        }),
      );
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
      throw err;
    }
  }

  @Put()
  async update(@Body() req: UpdateStickerTo, @Res() res: Response) {
    try {
      const sticker = await this.stickerService.updateSticker(req);
      const responseData: StickerResponseTo = {
        ...sticker,
        id: Number(sticker.id),
      };
      res.status(HttpStatus.OK).json(
        plainToInstance(StickerResponseTo, responseData, {
          excludeExtraneousValues: true,
        }),
      );
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
      throw err;
    }
  }
}
