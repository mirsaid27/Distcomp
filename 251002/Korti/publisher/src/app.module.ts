import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { EditorModule } from './providers/Editor/Editor.Module';

@Module({
  imports: [EditorModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
