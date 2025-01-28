export interface Article {
  id: bigint;
  editorId: bigint;
  title: string;
  content: string;
  created: dateTimeIso;
  modified: dateTimeIso;
}

type dateTimeIso = string;
