export interface Article {
  id: number;
  editorId: number;
  title: string;
  content: string;
  created: string;
  modified: string | null;
}
