import { Article } from 'src/schemas/Article';
import { Editor } from 'src/schemas/Editor';
import { Sticker } from 'src/schemas/Sticker';
import { Note } from 'src/schemas/Note';
import { ConflictException } from '@nestjs/common';

interface HasId {
  id: bigint;
}

export enum CollectionType {
  EDITORS = 'editors',
  ARTICLES = 'articles',
  STICKERS = 'stickers',
  NOTES = 'notes',
}

export class StorageService {
  private static editors: Editor[] = [];
  private static articles: Article[] = [];
  private static stickers: Sticker[] = [];
  private static notes: Note[] = [];

  private static collectionMap = {
    [CollectionType.EDITORS]: this.editors,
    [CollectionType.ARTICLES]: this.articles,
    [CollectionType.STICKERS]: this.stickers,
    [CollectionType.NOTES]: this.notes,
  };

  private static async getCollection<T extends HasId>(
    type: CollectionType,
  ): Promise<T[]> {
    const collection = this.collectionMap[type];

    if (!collection) {
      throw new Error(`Collection type ${type} not found`);
    }

    return collection as unknown as T[];
  }

  private static async store<T extends HasId>(
    type: CollectionType,
    item: T,
  ): Promise<T> {
    const collection = await this.getCollection<T>(type);
    let exists: boolean;
    switch (type) {
      case CollectionType.EDITORS:
        exists = (collection as unknown as Editor[]).some(
          (el) => el.login === (item as unknown as Editor).login,
        );
        break;
      default:
        exists = collection.some((el) => el.id === item.id);
    }
    if (!exists) {
      collection.push({ ...item });
      return item;
    } else {
      throw new ConflictException();
    }
  }

  private static async findById<T extends HasId>(
    type: CollectionType,
    id: bigint,
    errorMessage = 'Item not found',
  ): Promise<T> {
    const collection = await this.getCollection<T>(type);
    const item = collection.find((el) => el.id === id);

    if (!item) {
      throw new Error(errorMessage);
    }

    return item;
  }

  static async update<T extends HasId>(
    type: CollectionType,
    item: T,
  ): Promise<T> {
    const collection = await this.getCollection<T>(type);
    try {
      await this.findById(type, item.id);
    } catch {
      throw new ConflictException();
    }

    const index = collection.findIndex((el) => el.id === item.id);
    collection[index] = item;

    return item;
  }

  static async add<T extends HasId>(type: CollectionType, item: T): Promise<T> {
    return await this.store(type, item);
  }

  static async getAll<T extends HasId>(
    type: CollectionType,
  ): Promise<ReadonlyArray<T>> {
    const collection = await this.getCollection<T>(type);
    return [...collection];
  }

  static async getById<T extends HasId>(
    type: CollectionType,
    id: bigint,
  ): Promise<T> {
    return await this.findById(type, id);
  }

  static async remove<T extends HasId>(
    type: CollectionType,
    id: bigint,
  ): Promise<Readonly<T>> {
    const collection = await this.getCollection(type);
    const index = collection.findIndex((item) => item.id === id);

    if (index !== -1) {
      const item = collection[index] as T;
      collection.splice(index, 1);
      return item;
    } else {
      throw new ConflictException();
    }
  }

  static async generateId(type: CollectionType): Promise<bigint> {
    const collection = await this.getCollection(type);
    const existingIds = new Set(collection.map((item) => item.id));
    let newId = 1n;
    while (existingIds.has(newId)) {
      newId++;
    }
    return newId;
  }
}
