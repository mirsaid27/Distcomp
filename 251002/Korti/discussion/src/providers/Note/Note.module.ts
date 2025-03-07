import { Module } from '@nestjs/common';
import { NoteController } from './Note.controller';
import { NoteService } from './Note.service';
import { NoteRepository } from './Note.repository';
import { CassandraService } from 'src/cassandra/cassandra.service';

@Module({
  imports: [],
  providers: [NoteService, NoteRepository, CassandraService],
  controllers: [NoteController],
})
export class NoteModule {}
