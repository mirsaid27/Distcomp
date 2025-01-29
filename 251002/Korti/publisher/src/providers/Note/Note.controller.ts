import {
  Body,
  ConflictException,
  Controller,
  Delete,
  Get,
  HttpCode,
  HttpException,
  HttpStatus,
  NotFoundException,
  Param,
  ParseIntPipe,
  Post,
  Put,
  Res,
} from '@nestjs/common';
import { NoteService } from './Note.service';
import { Response } from 'express';
import { NoteRequestTo, UpdateNoteTo } from './Dto/NoteRequestTo';

@Controller('api/v1.0/notes')
export class NoteController {
  constructor(private readonly noteService: NoteService) {}

  @Get()
  @HttpCode(200)
  async getAll() {
    return await this.noteService.getAllNotes();
  }

  @Get(':id')
  async getById(@Param('id', ParseIntPipe) id: number, @Res() res: Response) {
    try {
      const resBody = await this.noteService.getNoteById(id);
      res.status(HttpStatus.OK).json(resBody);
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40404,
            errorMessage: 'Note does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
    }
  }

  @Post()
  async createNote(@Body() body: NoteRequestTo, @Res() res: Response) {
    try {
      const resBody = await this.noteService.createNote(body);
      res.status(HttpStatus.CREATED).json(resBody);
    } catch (err) {
      if (err instanceof NotFoundException) {
        throw new HttpException(
          {
            errorCode: 40403,
            errorMessage: 'Article does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
    }
  }

  @Delete(':id')
  async delete(@Param('id', ParseIntPipe) id: number, @Res() res: Response) {
    try {
      await this.noteService.deleteNote(id);
      res.status(204);
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40404,
            errorMessage: 'Note does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
    }
  }

  @Put()
  async update(@Body() body: UpdateNoteTo, @Res() res: Response) {
    try {
      const note = await this.noteService.updateNote(body);
      res.status(HttpStatus.OK).json(note);
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40404,
            errorMessage: 'Note does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
    }
  }
}
