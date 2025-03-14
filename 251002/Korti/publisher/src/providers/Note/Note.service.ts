import {
  HttpException,
  HttpStatus,
  Injectable,
  InternalServerErrorException,
  NotFoundException,
} from '@nestjs/common';
import { NoteResponseTo } from './Dto/NoteResponseTo';
import { NoteRequestTo, UpdateNoteTo } from './Dto/NoteRequestTo';
import { InjectRepository } from '@nestjs/typeorm';
import { Article } from '../../entities/Article';
import { Repository } from 'typeorm';
import axios from 'axios';
import path from 'path';

const DISCUSSION_URL = 'http://localhost:24130/api/v1.0/notes';

@Injectable()
export class NoteService {
  constructor(
    @InjectRepository(Article)
    private readonly articleRepository: Repository<Article>,
  ) {}

  async getAllNotes(): Promise<ReadonlyArray<NoteResponseTo>> {
    const response = axios.get<ReadonlyArray<NoteResponseTo>>(DISCUSSION_URL);
    return (await response).data;
  }

  async createNote(item: NoteRequestTo): Promise<NoteResponseTo> {
    try {
      const article = await this.articleRepository.findOne({
        where: { id: item.articleId },
      });
      if (!article) throw new NotFoundException();
      item.id = Math.floor(Math.random() * (10000 - 100 + 1)) + 100;
      const response = axios.post<NoteResponseTo>(DISCUSSION_URL, item);
      return (await response).data;
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
    const response = axios.get<NoteResponseTo>(
      path.resolve(DISCUSSION_URL, id.toString()),
    );
    return (await response).data;
  }

  async deleteNote(id: number): Promise<void> {
    const response = axios.delete(path.resolve(DISCUSSION_URL, id.toString()));
    await response;
  }

  async updateNote(body: UpdateNoteTo): Promise<NoteResponseTo> {
    try {
      const article = await this.articleRepository.findOne({
        where: { id: body.articleId },
      });
      if (!article) throw new NotFoundException();
      const response = axios.put<NoteResponseTo>(DISCUSSION_URL);
      return (await response).data;
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

  // async getNotes(id: number): Promise<NoteResponseTo[]> {
  //   try {
  //     const article = await this.articleRepository.findOne({ where: { id } });
  //     if (!article) throw new ConflictException();
  //     const notes: Note[] = await this.noteRepository.find({
  //       where: { articleId: id },
  //     });
  //     return notes;
  //   } catch (err) {
  //     if (err instanceof ConflictException) {
  //       throw new HttpException(
  //         {
  //           errorCode: 40403,
  //           errorMessage: 'Article does not exist.',
  //         },
  //         HttpStatus.NOT_FOUND,
  //       );
  //     }
  //     throw new InternalServerErrorException();
  //   }
  // }
}
