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
  UsePipes,
  ValidationPipe,
} from '@nestjs/common';
import { EditorService } from './Editor.Service';
import { EditorResponseTo } from './Dto/EditorResponseTo';
import { Response } from 'express';
import { plainToInstance } from 'class-transformer';
import { EditorRequestTo, UpdateEditorDto } from './Dto/EditorRequestTo';
import { Editor } from 'src/schemas/Editor';

@Controller('api/v1.0/editors')
export class EditorController {
  private editorService: EditorService = new EditorService();

  @Get()
  async findAllEditors(@Res() res: Response): Promise<void> {
    const editors = await this.editorService.getAllEditors();
    const responseData = editors.map((el) => {
      return {
        ...el,
        id: el.id.toString(),
      };
    });
    res.status(HttpStatus.OK).json(
      plainToInstance(EditorResponseTo, responseData, {
        excludeExtraneousValues: true,
      }),
    );
  }

  @Get(':id')
  async findEditorById(
    @Param('id', ParseIntPipe) idEditor: number,
    @Res() res: Response,
  ): Promise<void> {
    try {
      const editor = await this.editorService.findById(BigInt(idEditor));
      const responseData = {
        ...editor,
        id: editor.id.toString(),
      };
      res.status(HttpStatus.OK).json(
        plainToInstance(EditorResponseTo, responseData, {
          excludeExtraneousValues: true,
        }),
      );
    } catch (err) {
      if (err instanceof ConflictException) {
        res
          .status(HttpStatus.BAD_REQUEST)
          .json({ errorCode: 40000, errorMessage: 'Editor does not exist.' });
      }
    }
  }

  @Post()
  @UsePipes(new ValidationPipe())
  async createEditor(
    @Body() req: EditorRequestTo,
    @Res() res: Response,
  ): Promise<void> {
    try {
      const editor = await this.editorService.createEditor(req);
      const responseData = {
        ...editor,
        id: editor.id.toString(),
      };
      console.log(JSON.stringify(responseData));
      res.status(HttpStatus.CREATED).json(
        plainToInstance(EditorResponseTo, responseData, {
          excludeExtraneousValues: true,
        }),
      );
    } catch (err) {
      if (err instanceof ConflictException) {
        res
          .status(HttpStatus.BAD_REQUEST)
          .json({ errorCode: 40001, errorMessage: 'Editor already exist.' });
      }
    }
  }

  @Delete(':id')
  @UsePipes(new ValidationPipe())
  async deleteEditor(
    @Param('id', ParseIntPipe) idEditor: number,
    @Res() res: Response,
  ): Promise<void> {
    try {
      console.log('123', idEditor);
      const editor = await this.editorService.deleteEditor(BigInt(idEditor));
      const responseData = {
        ...editor,
        id: editor.id.toString(),
      };
      res.status(HttpStatus.NO_CONTENT).json(
        plainToInstance(EditorResponseTo, responseData, {
          excludeExtraneousValues: true,
        }),
      );
    } catch (err) {
      if (err instanceof ConflictException) {
        res
          .status(HttpStatus.NOT_FOUND)
          .json({ errorCode: 40002, errorMessage: 'Editor does not exist.' });
      }
    }
  }

  @Put()
  @UsePipes(new ValidationPipe())
  async updateEditor(@Body() body: UpdateEditorDto, @Res() res: Response) {
    try {
      const responseBody: Editor = {
        ...body,
        id: BigInt(body.id),
      };
      const editor = await this.editorService.updateEditor(responseBody);
      const responseData = {
        ...editor,
        id: editor.id.toString(),
      };
      res.status(HttpStatus.OK).json(
        plainToInstance(EditorResponseTo, responseData, {
          excludeExtraneousValues: true,
        }),
      );
    } catch (err) {
      if (err instanceof ConflictException) {
        res
          .status(HttpStatus.NOT_FOUND)
          .json({ errorCode: 40002, errorMessage: 'Editor does not exist.' });
      }
    }
  }
}
