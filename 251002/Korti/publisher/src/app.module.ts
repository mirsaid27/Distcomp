import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { EditorModule } from './providers/Editor/Editor.Module';
import { ArticleModule } from './providers/Article/Article.module';
import { StickerModule } from './providers/Sticker/Sticker.module';
import { MiddlewareConsumer } from '@nestjs/common';
import { JsonHeadersMiddleware } from './middleware/jsonMiddleware';
import { NoteModule } from './providers/Note/Note.module';
import { TypeOrmModule } from '@nestjs/typeorm';
@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: 'postgres',
      host: 'localhost',
      port: 5432,
      username: 'postgres',
      password: 'postgres',
      database: 'distcomp',
      entities: [],
      synchronize: true,
    }),
    EditorModule,
    ArticleModule,
    StickerModule,
    NoteModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {
  configure(consumer: MiddlewareConsumer) {
    consumer.apply(JsonHeadersMiddleware).forRoutes('*');
  }
}
