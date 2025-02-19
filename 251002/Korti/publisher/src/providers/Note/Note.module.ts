import { Module } from '@nestjs/common';
import { NoteController } from './Note.controller';
import { NoteService } from './Note.service';

@Module({
  imports: [],
  providers: [NoteService],
  controllers: [NoteController],
})
export class NoteModule {}
