import {
  ConflictException,
  HttpException,
  HttpStatus,
  Injectable,
  InternalServerErrorException,
  NotFoundException,
} from '@nestjs/common';
import { Editor } from 'src/entities/Editor';
import { StorageService } from 'src/storage/database';
import { EditorRequestTo } from './Dto/EditorRequestTo';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

@Injectable()
export class EditorService {
  constructor(
    @InjectRepository(Editor)
    private editorRepository: Repository<Editor>,
  ) {}

  async getAllEditors(): Promise<ReadonlyArray<Editor>> {
    return await this.editorRepository.find();
  }

  async createEditor(editor: EditorRequestTo): Promise<Editor> {
    try {
      const newEditor = this.editorRepository.create(editor);
      return await this.editorRepository.save(newEditor);
    } catch (err) {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      if ((err.code as string) === '23505') {
        throw new HttpException(
          {
            errorCode: 40001,
            errorMessage: 'Editor already exist.',
          },
          HttpStatus.FORBIDDEN,
        );
      }
      throw new InternalServerErrorException('Unexpected error');
    }
  }

  async deleteEditor(id: number): Promise<void> {
    try {
      const editor = await this.editorRepository.findOneBy({ id });
      if (!editor) throw new ConflictException();
      await this.editorRepository.delete(id);
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40400,
            errorMessage: 'Editor does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
      throw new InternalServerErrorException('Unexpected error');
    }
  }

  async findById(id: number): Promise<Editor> {
    try {
      const editor = await this.editorRepository.findOneBy({ id });
      if (editor) return editor;
      else
        throw new HttpException(
          {
            errorCode: 40400,
            errorMessage: 'Editor does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
    } catch {
      throw new InternalServerErrorException('Unexpected error');
    }
  }

  async updateEditor(item: Editor): Promise<Editor> {
    try {
      const existEditor = await this.editorRepository.findOne({
        where: { id: item.id },
      });
      if (!existEditor) {
        throw new HttpException(
          {
            errorCode: 40400,
            errorMessage: 'Editor does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
      await this.editorRepository.update(item.id, {
        firstname: item.firstname,
        lastname: item.lastname,
        login: item.login,
        password: item.password,
      });
      const result = await this.editorRepository.findOne({
        where: { id: item.id },
      });
      if (!result) throw new NotFoundException();
      return result;
    } catch {
      throw new InternalServerErrorException('Unexpected error');
    }
  }

  async getEditorByArticleId(articleId: number): Promise<Editor> {
    try {
      const editor = await StorageService.getEditorByArticleId(articleId);
      return editor;
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40400,
            errorMessage: 'Article does not exist.',
          },
          HttpStatus.NOT_FOUND,
        );
      }
      throw new InternalServerErrorException('Unexpected error');
    }
  }
}
