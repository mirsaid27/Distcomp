import {
  Body,
  ConflictException,
  Controller,
  Delete,
  Get,
  HttpStatus,
  Param,
  ParseIntPipe,
  Put,
  Post,
  Res,
  HttpException,
} from '@nestjs/common';
import { EditorService } from './Editor.Service';
import { EditorResponseTo } from './Dto/EditorResponseTo';
import { Response } from 'express';
import { plainToInstance } from 'class-transformer';
import { EditorRequestTo, UpdateEditorDto } from './Dto/EditorRequestTo';
import { Editor } from 'src/entities/Editor';

@Controller('api/v1.0/editors')
export class EditorController {
  private editorService: EditorService = new EditorService();

  @Get()
  async findAll(@Res() res: Response): Promise<void> {
    const editors = await this.editorService.getAllEditors();
    const responseData: EditorResponseTo[] = editors.map((el) => {
      return {
        ...el,
        id: Number(el.id),
      };
    });
    res.status(HttpStatus.OK).json(
      plainToInstance(EditorResponseTo, responseData, {
        excludeExtraneousValues: true,
      }),
    );
  }

  @Get(':id')
  async findById(
    @Param('id', ParseIntPipe) idEditor: number,
    @Res() res: Response,
  ): Promise<void> {
    try {
      const editor = await this.editorService.findById(idEditor);
      const responseData = {
        ...editor,
        id: Number(editor.id),
      };
      res.status(HttpStatus.OK).json(
        plainToInstance(EditorResponseTo, responseData, {
          excludeExtraneousValues: true,
        }),
      );
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
      throw err;
    }
  }

  @Post()
  async create(
    @Body() req: EditorRequestTo,
    @Res() res: Response,
  ): Promise<void> {
    try {
      const editor: Editor = await this.editorService.createEditor(req);
      res.status(HttpStatus.CREATED).json(
        plainToInstance(EditorResponseTo, editor, {
          excludeExtraneousValues: true,
        }),
      );
    } catch (err) {
      if (err instanceof ConflictException) {
        throw new HttpException(
          {
            errorCode: 40001,
            errorMessage: 'Editor already exist.',
          },
          HttpStatus.BAD_REQUEST,
        );
      }
      throw err;
    }
  }

  @Delete(':id')
  async delete(
    @Param('id', ParseIntPipe) idEditor: number,
    @Res() res: Response,
  ): Promise<void> {
    try {
      await this.editorService.deleteEditor(idEditor);
      res.status(HttpStatus.NO_CONTENT).send();
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
      throw err;
    }
  }

  @Put()
  async update(@Body() body: UpdateEditorDto, @Res() res: Response) {
    try {
      const responseBody: Editor = {
        ...body,
        id: Number(body.id),
      };
      const editor = await this.editorService.updateEditor(responseBody);
      const responseData: EditorResponseTo = {
        ...editor,
        id: Number(editor.id),
      };
      res.status(HttpStatus.OK).json(
        plainToInstance(EditorResponseTo, responseData, {
          excludeExtraneousValues: true,
        }),
      );
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
      throw err;
    }
  }

  @Get('article/:articleId')
  async getByarticleId(
    @Param('articleId', ParseIntPipe) articleId: number,
    @Res() res: Response,
  ) {
    try {
      const editor = await this.editorService.getEditorByArticleId(articleId);
      res.status(HttpStatus.OK).json(plainToInstance(EditorResponseTo, editor));
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
      throw err;
    }
  }
}
