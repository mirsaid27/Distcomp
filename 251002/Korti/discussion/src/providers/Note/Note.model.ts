// note.model.ts
export class Note {
  id: bigint;
  articleId: bigint;
  content: string;
  country: string;

  constructor(id: bigint, articleId: bigint, content: string, country: string) {
    this.id = id;
    this.articleId = articleId;
    this.content = content;
    this.country = country;
  }
}
