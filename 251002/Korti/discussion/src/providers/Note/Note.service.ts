import {
  HttpException,
  HttpStatus,
  Injectable,
  InternalServerErrorException,
} from '@nestjs/common';
// import {
//   ConflictException,
//   HttpException,
//   HttpStatus,
//   Injectable,
//   InternalServerErrorException,
//   NotFoundException,
// } from '@nestjs/common';
import { NoteResponseTo } from './Dto/NoteResponseTo';
import { plainToInstance } from 'class-transformer';
import { Client, mapping } from 'cassandra-driver';
import { Note } from './Note.model';
import { CassandraService } from 'src/cassandra/cassandra.service';
import { NoteRequestTo, UpdateNoteTo } from './Dto/NoteRequestTo';
// import { NoteRequestTo, UpdateNoteTo } from './Dto/NoteRequestTo';

@Injectable()
export class NoteService {
  private client: Client;
  private noteMapper: mapping.ModelMapper<Note>;

  constructor(cassandraService: CassandraService) {
    this.noteMapper = cassandraService
      .createMapper({
        models: {
          Note: {
            tables: ['tbl_note'],
          },
        },
      })
      .forModel('Note');
    this.client = cassandraService.client;
  }

  async getAllNotes(): Promise<ReadonlyArray<NoteResponseTo>> {
    const notes = (await this.noteMapper.findAll()).toArray();
    return plainToInstance(NoteResponseTo, notes, {
      excludeExtraneousValues: true,
    });
  }

  async createNote(item: NoteRequestTo): Promise<NoteResponseTo> {
    const note = plainToInstance(Note, item);
    try {
      note.country = 'Belarus';
      await this.noteMapper.insert(note);
      const createdNote = await this.getNoteById(note.id);
      return plainToInstance(NoteResponseTo, createdNote, {
        excludeExtraneousValues: true,
      });
    } catch {
      throw new HttpException(
        {
          errorCode: 40005,
          errorMessage: 'Note already exist.',
        },
        HttpStatus.FORBIDDEN,
      );
    }
  }

  async getNoteById(id: number): Promise<NoteResponseTo> {
    const note = await this.client.execute(
      `SELECT * FROM tbl_note WHERE id = ? ALLOW FILTERING`,
      [id],
      {
        prepare: true,
      },
    );
    console.log(note);
    return plainToInstance(NoteResponseTo, note, {
      excludeExtraneousValues: true,
    });
  }

  async deleteNote(id: number): Promise<void> {
    try {
      const note = await this.getNoteById(id);
      await this.noteMapper.remove(note);
    } catch {
      throw new HttpException(
        {
          errorCode: 40404,
          errorMessage: 'Note does not exist.',
        },
        HttpStatus.NOT_FOUND,
      );
    }
  }

  async updateNote(body: UpdateNoteTo): Promise<NoteResponseTo> {
    try {
      const note = await this.getNoteById(body.id);
      if (!note) {
        throw new HttpException(
          {
            errorCode: 40404,
            errorMessage: 'Note does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
      note.content = body.content;
      note.articleId = body.articleId;
      return plainToInstance(
        NoteResponseTo,
        (await this.noteMapper.update(note)).first(),
        { excludeExtraneousValues: true },
      );
    } catch {
      throw new InternalServerErrorException();
    }
  }
}
