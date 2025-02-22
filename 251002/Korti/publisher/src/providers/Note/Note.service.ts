import {
  ConflictException,
  HttpException,
  HttpStatus,
  Injectable,
  InternalServerErrorException,
  NotFoundException,
} from '@nestjs/common';
import { Note } from 'src/entities/Note';
import { CollectionType, StorageService } from 'src/storage/database';
import { NoteResponseTo } from './Dto/NoteResponseTo';
import { plainToInstance } from 'class-transformer';
import { NoteRequestTo, UpdateNoteTo } from './Dto/NoteRequestTo';

@Injectable()
export class NoteService {
  async getAllNotes(): Promise<ReadonlyArray<NoteResponseTo>> {
    const notes = await StorageService.getAll<Note>(CollectionType.NOTES);
    const responseData = notes.map((el) => {
      return {
        ...el,
        id: Number(el.id),
      };
    });
    return plainToInstance(NoteResponseTo, responseData, {
      excludeExtraneousValues: true,
    });
  }

  async createNote(item: NoteRequestTo): Promise<Note> {
    try {
      const note = await StorageService.add<Note>(
        CollectionType.NOTES,
        item as Note,
      );
      return plainToInstance(NoteResponseTo, note);
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
      throw new InternalServerErrorException();
    }
  }

  async getNoteById(id: number): Promise<NoteResponseTo> {
    try {
      const note = await StorageService.getById<Note>(CollectionType.NOTES, id);
      return plainToInstance(NoteResponseTo, note);
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
      throw new InternalServerErrorException();
    }
  }

  async deleteNote(id: number): Promise<void> {
    try {
      await StorageService.remove<Note>(CollectionType.NOTES, id);
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
      throw new InternalServerErrorException();
    }
  }

  async updateNote(body: UpdateNoteTo): Promise<NoteResponseTo> {
    try {
      const note = await StorageService.update<Note>(
        CollectionType.NOTES,
        body,
      );
      return plainToInstance(NoteResponseTo, note);
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
      throw new InternalServerErrorException();
    }
  }
}
