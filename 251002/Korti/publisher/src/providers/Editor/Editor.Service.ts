import {
  ConflictException,
  Injectable,
  InternalServerErrorException,
} from '@nestjs/common';
import { Editor } from 'src/entities/Editor';
import { CollectionType, StorageService } from 'src/storage/database';
import { EditorRequestTo } from './Dto/EditorRequestTo';

@Injectable()
export class EditorService {
  async getAllEditors(): Promise<ReadonlyArray<Editor>> {
    return await StorageService.getAll<Editor>(CollectionType.EDITORS);
  }

  async createEditor(editor: EditorRequestTo): Promise<Editor> {
    try {
      const id: number = await StorageService.generateId(
        CollectionType.EDITORS,
      );
      const editorBody: Editor = { id, ...editor };
      return await StorageService.add<Editor>(
        CollectionType.EDITORS,
        editorBody,
      );
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new ConflictException();
      }
      throw new InternalServerErrorException('Unexpected error');
    }
  }

  async deleteEditor(id: number): Promise<Editor> {
    try {
      const editor = await StorageService.remove<Editor>(
        CollectionType.EDITORS,
        id,
      );
      return editor;
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new ConflictException();
      }
      throw new InternalServerErrorException('Unexpected error');
    }
  }

  async findById(id: number): Promise<Editor> {
    try {
      const editor = await StorageService.getById<Editor>(
        CollectionType.EDITORS,
        id,
      );
      return editor;
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new ConflictException();
      }
      throw new InternalServerErrorException('Unexpected error');
    }
  }

  async updateEditor(item: Editor): Promise<Editor> {
    try {
      const editor = await StorageService.update(CollectionType.EDITORS, item);
      return editor;
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new ConflictException();
      }
      throw new InternalServerErrorException('Unexpected error');
    }
  }

  async getEditorByArticleId(articleId: number): Promise<Editor> {
    try {
      const editor = await StorageService.getEditorByArticleId(articleId);
      return editor;
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new ConflictException();
      }
      throw new InternalServerErrorException('Unexpected error');
    }
  }
}
