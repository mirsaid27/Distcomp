import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { EditorModule } from './providers/Editor/Editor.Module';
import { ArticleModule } from './providers/Article/Article.module';
import { StickerModule } from './providers/Sticker/Sticker.module';
import { MiddlewareConsumer } from '@nestjs/common';
import { JsonHeadersMiddleware } from './middleware/jsonMiddleware';
import { NoteModule } from './providers/Note/Note.module';
@Module({
  imports: [EditorModule, ArticleModule, StickerModule, NoteModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {
  configure(consumer: MiddlewareConsumer) {
    consumer.apply(JsonHeadersMiddleware).forRoutes('*');
  }
}
