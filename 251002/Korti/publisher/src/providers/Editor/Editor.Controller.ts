import {
  Body,
  Controller,
  Delete,
  Get,
  HttpStatus,
  Param,
  ParseIntPipe,
  Put,
  Post,
  Res,
} from '@nestjs/common';
import { EditorService } from './Editor.Service';
import { EditorResponseTo } from './Dto/EditorResponseTo';
import { Response } from 'express';
import { plainToInstance } from 'class-transformer';
import { EditorRequestTo, UpdateEditorDto } from './Dto/EditorRequestTo';
import { Editor } from 'src/entities/Editor';

@Controller('api/v1.0/editors')
export class EditorController {
  constructor(private readonly editorService: EditorService) {}

  @Get()
  async findAll(@Res() res: Response): Promise<void> {
    const editors = await this.editorService.getAllEditors();
    res.status(HttpStatus.OK).json(
      plainToInstance(EditorResponseTo, editors, {
        excludeExtraneousValues: true,
      }),
    );
  }

  @Get(':id')
  async findById(
    @Param('id', ParseIntPipe) idEditor: number,
    @Res() res: Response,
  ): Promise<void> {
    const editor = await this.editorService.findById(idEditor);
    res.status(HttpStatus.OK).json(
      plainToInstance(EditorResponseTo, editor, {
        excludeExtraneousValues: true,
      }),
    );
  }

  @Post()
  async create(
    @Body() req: EditorRequestTo,
    @Res() res: Response,
  ): Promise<void> {
    console.log(req);
    const editor: Editor = await this.editorService.createEditor(req);
    res.status(HttpStatus.CREATED).json(
      plainToInstance(EditorResponseTo, editor, {
        excludeExtraneousValues: true,
      }),
    );
  }

  @Delete(':id')
  async delete(
    @Param('id', ParseIntPipe) idEditor: number,
    @Res() res: Response,
  ): Promise<void> {
    await this.editorService.deleteEditor(idEditor);
    res.status(HttpStatus.NO_CONTENT).send();
  }

  @Put()
  async update(@Body() body: UpdateEditorDto, @Res() res: Response) {
    const editorForArticles = await this.editorService.findById(body.id);
    const responseBody: Editor = {
      ...body,
      articles: editorForArticles.articles,
    };
    const editor = await this.editorService.updateEditor(responseBody);
    res.status(HttpStatus.OK).json(
      plainToInstance(EditorResponseTo, editor, {
        excludeExtraneousValues: true,
      }),
    );
  }

  @Get('article/:articleId')
  async getByarticleId(
    @Param('articleId', ParseIntPipe) articleId: number,
    @Res() res: Response,
  ) {
    const editor = await this.editorService.getEditorByArticleId(articleId);
    res.status(HttpStatus.OK).json(plainToInstance(EditorResponseTo, editor));
  }
}
