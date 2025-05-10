from pydantic import BaseModel
from typing import Optional, List
from datetime import datetime

class WriterRequestTo(BaseModel):
    login: str
    password: str
    firstname: str
    lastname: str

class WriterResponseTo(WriterRequestTo):
    id: int

class NewsRequestTo(BaseModel):
    writer_id: int
    title: str
    content: str

class NewsResponseTo(NewsRequestTo):
    id: int
    created: datetime
    modified: datetime

class NewsUpdateTo(BaseModel):
    writer_id: int
    title: str
    content: str

class LabelRequestTo(BaseModel):
    name: str

class LabelResponseTo(LabelRequestTo):
    id: int

class MessageRequestTo(BaseModel):
    news_id: int
    content: str

class MessageResponseTo(MessageRequestTo):
    id: int
