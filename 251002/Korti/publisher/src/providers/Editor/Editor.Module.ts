import { Module } from '@nestjs/common';
import { EditorService } from './Editor.Service';
import { EditorController } from './Editor.Controller';

@Module({
  imports: [],
  controllers: [EditorController],
  providers: [EditorService],
})
export class EditorModule {}
