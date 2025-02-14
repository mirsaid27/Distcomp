import { DataSource } from 'typeorm';
import 'reflect-metadata';
import { Editor } from './entities/Editor';
import { Article } from './entities/Article';
import { Note } from './entities/Note';
import { Sticker } from './entities/Sticker';

const AppDataSource = new DataSource({
  type: 'postgres',
  host: 'localhost',
  port: 5432,
  username: 'postgres',
  password: 'postgres',
  database: 'distcomp',
  entities: [Editor, Article, Note, Sticker],
  migrations: ['src/migrations/**/*.ts'],
  migrationsTableName: 'test-migration',
  logging: true,
});

export default AppDataSource;
