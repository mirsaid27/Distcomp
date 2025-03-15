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
    const response = await axios
      .get<NoteResponseTo>(`${DISCUSSION_URL}/${id.toString()}`)
      .catch((err) => {
        // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
        if (err.response?.status === 404) {
          throw new HttpException(
            {
              errorCode: 40404,
              errorMessage: 'Note does not exist.',
            },
            HttpStatus.NOT_FOUND,
          );
        }
        throw new HttpException(
          {
            errorCode: 50000,
            errorMessage: 'Internal server error.',
          },
          HttpStatus.INTERNAL_SERVER_ERROR,
        );
      });
    return response.data;
  }

  async deleteNote(id: number): Promise<void> {
    await axios.delete(`${DISCUSSION_URL}/${id.toString()}`).catch((err) => {
      console.log(err);
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      if (err.response?.status === 404) {
        throw new HttpException(
          {
            errorCode: 40404,
            errorMessage: 'Note does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
      throw new HttpException(
        {
          errorCode: 50000,
          errorMessage: 'Internal server error.',
        },
        HttpStatus.INTERNAL_SERVER_ERROR,
      );
    });
  }

  async updateNote(body: UpdateNoteTo): Promise<NoteResponseTo> {
    try {
      const article = await this.articleRepository.findOne({
        where: { id: body.articleId },
      });
      if (!article) throw new NotFoundException();
      const response = axios.put<NoteResponseTo>(DISCUSSION_URL, body);
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
