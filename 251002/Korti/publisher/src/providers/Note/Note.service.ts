import {
  ConflictException,
  HttpException,
  HttpStatus,
  Injectable,
  InternalServerErrorException,
  NotFoundException,
} from '@nestjs/common';
import { Note } from 'src/entities/Note';
import { NoteResponseTo } from './Dto/NoteResponseTo';
import { plainToInstance } from 'class-transformer';
import { NoteRequestTo, UpdateNoteTo } from './Dto/NoteRequestTo';
import { InjectRepository } from '@nestjs/typeorm';
import { Article } from 'src/entities/Article';
import { Repository } from 'typeorm';

@Injectable()
export class NoteService {
  constructor(
    @InjectRepository(Article)
    private readonly articleRepository: Repository<Article>,
    @InjectRepository(Note)
    private readonly noteRepository: Repository<Note>,
  ) {}

  async getAllNotes(): Promise<ReadonlyArray<NoteResponseTo>> {
    const notes = await this.noteRepository.find();
    console.log(notes);
    return plainToInstance(NoteResponseTo, notes, {
      excludeExtraneousValues: true,
    });
  }

  async createNote(item: NoteRequestTo): Promise<NoteResponseTo> {
    try {
      const article = await this.articleRepository.findOne({
        where: { id: item.articleId },
      });
      if (!article) throw new NotFoundException();
      const note = this.noteRepository.create(item);
      return plainToInstance(
        NoteResponseTo,
        await this.noteRepository.save(note),
        { excludeExtraneousValues: true },
      );
    } catch (err) {
      if (err instanceof NotFoundException) {
        throw new HttpException(
          {
            errorCode: 40403,
            errorMessage: 'Article does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
        // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      } else if ((err.code as string) === '23505') {
        throw new HttpException(
          {
            errorCode: 40005,
            errorMessage: 'Note already exist.',
          },
          HttpStatus.FORBIDDEN,
        );
      }
      throw new InternalServerErrorException();
    }
  }

  async getNoteById(id: number): Promise<NoteResponseTo> {
    try {
      const note = await this.noteRepository.findOne({ where: { id } });
      if (!note) throw new ConflictException();
      return plainToInstance(NoteResponseTo, note, {
        excludeExtraneousValues: true,
      });
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
      const note = await this.noteRepository.findOne({ where: { id } });
      if (!note) throw new ConflictException();
      await this.noteRepository.delete(note);
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
      const article = await this.articleRepository.findOne({
        where: { id: body.articleId },
      });
      if (!article) throw new NotFoundException();
      const note = await this.noteRepository.findOne({
        where: { id: body.id },
      });
      if (!note) throw new ConflictException();
      await this.noteRepository.update(body.id, {
        article: article,
        articleId: body.articleId,
        content: body.content,
      });
      const updNote = await this.noteRepository.findOne({
        where: { id: body.id },
      });
      if (!updNote) throw new Error();
      return plainToInstance(NoteResponseTo, updNote, {
        excludeExtraneousValues: true,
      });
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40404,
            errorMessage: 'Note does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      } else if (err instanceof NotFoundException) {
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

  async getNotes(id: number): Promise<NoteResponseTo[]> {
    try {
      const article = await this.articleRepository.findOne({ where: { id } });
      if (!article) throw new ConflictException();
      const notes: Note[] = await this.noteRepository.find({
        where: { articleId: id },
      });
      return notes;
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
