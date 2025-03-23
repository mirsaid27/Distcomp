import { Module } from '@nestjs/common';
import { NoteController } from './Note.controller';
import { NoteService } from './Note.service';
import { CassandraService } from 'src/cassandra/cassandra.service';

@Module({
  imports: [],
  providers: [NoteService, CassandraService],
  controllers: [NoteController],
})
export class NoteModule {}
