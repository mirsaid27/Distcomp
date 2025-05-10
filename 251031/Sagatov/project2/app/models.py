from pydantic import BaseModel, Field, ConfigDict, EmailStr
from typing import Union, List, Optional
from datetime import datetime
from sqlalchemy import Column, Integer, String, Text, DateTime, ForeignKey, Table
from sqlalchemy.orm import relationship, declarative_base

Base = declarative_base()

# Association table for many-to-many relationship between News and Label
tbl_news_label = Table(
    'tbl_news_label', Base.metadata,
    Column('news_id', Integer, ForeignKey('tbl_news.id'), primary_key=True),
    Column('label_id', Integer, ForeignKey('tbl_label.id'), primary_key=True),
    schema='distcomp'
)

class Writer(Base):
    __tablename__ = 'tbl_writer'
    __table_args__ = {'schema': 'distcomp'}
    
    id = Column(Integer, primary_key=True, index=True)
    login = Column(String(64), unique=True, nullable=False)
    password = Column(String(128), nullable=False)
    firstname = Column(String(64), nullable=False)
    lastname = Column(String(64), nullable=False)
    
    news = relationship("News", back_populates="writer")

class News(Base):
    __tablename__ = 'tbl_news'
    __table_args__ = {'schema': 'distcomp'}
    
    id = Column(Integer, primary_key=True, index=True)
    writer_id = Column(Integer, ForeignKey('distcomp.tbl_writer.id'), nullable=False)
    title = Column(String(64), nullable=False)
    content = Column(Text, nullable=False)
    created = Column(DateTime, default=datetime.utcnow, nullable=False)
    modified = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow, nullable=False)
    
    writer = relationship("Writer", back_populates="news")
    messages = relationship("Message", back_populates="news")
    labels = relationship("Label", secondary=tbl_news_label, back_populates="news")

class Label(Base):
    __tablename__ = 'tbl_label'
    __table_args__ = {'schema': 'distcomp'}
    
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(32), unique=True, nullable=False)
    
    news = relationship("News", secondary=tbl_news_label, back_populates="labels")

class Message(Base):
    __tablename__ = 'tbl_message'
    __table_args__ = {'schema': 'distcomp'}
    
    id = Column(Integer, primary_key=True, index=True)
    news_id = Column(Integer, ForeignKey('distcomp.tbl_news.id'), nullable=False)
    content = Column(String(2048), nullable=False)
    
    news = relationship("News", back_populates="messages")

# Pydantic models (DTOs)
class WriterRequestTo(BaseModel):
    login: EmailStr = Field(..., examples=["user@example.com"])
    password: str = Field(..., min_length=8, max_length=128, examples=["strongpassword"])
    firstname: str = Field(..., min_length=2, max_length=64, examples=["John"])
    lastname: str = Field(..., min_length=2, max_length=64, examples=["Doe"])

class NewsRequestTo(BaseModel):
    writer_id: int = Field(..., alias="writerId", examples=[1])
    title: str = Field(..., min_length=1, max_length=64, examples=["Breaking News"])
    content: str = Field(..., min_length=1, examples=["News content here..."])

    model_config = ConfigDict(populate_by_name=True)

class LabelRequestTo(BaseModel):
    name: str = Field(..., min_length=2, max_length=32, examples=["Important"])

class MessageRequestTo(BaseModel):
    news_id: Union[int, str] = Field(..., alias="newsId", examples=[1])
    content: str = Field(..., min_length=2, max_length=2048, examples=["Great article!"])

    model_config = ConfigDict(populate_by_name=True)

# Response DTOs
class WriterResponseTo(BaseModel):
    id: int = Field(..., examples=[1])
    login: EmailStr = Field(..., examples=["user@example.com"])
    password: str = Field(..., min_length=8, max_length=128, examples=["strongpassword"])
    firstname: str = Field(..., min_length=2, max_length=64, examples=["John"])
    lastname: str = Field(..., min_length=2, max_length=64, examples=["Doe"])

class NewsResponseTo(BaseModel):
    id: int = Field(..., examples=[1])
    writer_id: int = Field(..., alias="writerId", examples=[1])
    title: str = Field(..., examples=["Breaking News"])
    content: str = Field(..., examples=["News content here..."])
    created: datetime = Field(..., examples=["2023-01-01T00:00:00"])
    modified: datetime = Field(..., examples=["2023-01-01T00:00:00"])

    model_config = ConfigDict(populate_by_name=True)

class LabelResponseTo(BaseModel):
    id: int = Field(..., examples=[1])
    name: str = Field(..., min_length=2, max_length=32, examples=["Important"])

class MessageResponseTo(BaseModel):
    id: int = Field(..., examples=[1])
    news_id: int = Field(..., alias="newsId", examples=[1])
    content: str = Field(..., min_length=2, max_length=2048, examples=["Great article!"])

    model_config = ConfigDict(populate_by_name=True)

# Update DTOs
class NewsUpdateTo(BaseModel):
    id: Union[str, int] = Field(..., examples=[1])
    title: str = Field(..., min_length=1, max_length=64, examples=["Updated News"])
    content: str = Field(..., min_length=1, examples=["Updated content..."])
    writer_id: int = Field(..., alias="writerId", examples=[1])

    model_config = ConfigDict(populate_by_name=True)

class MessageUpdateTo(BaseModel):
    id: int = Field(..., examples=[1])
    content: str = Field(..., min_length=2, max_length=2048, examples=["Updated comment"])
    news_id: Union[int, str] = Field(..., alias="newsId", examples=[1])

    model_config = ConfigDict(populate_by_name=True)

class LabelUpdateTo(BaseModel):
    id: int = Field(..., examples=[1])
    name: str = Field(..., min_length=2, max_length=32, examples=["Updated Label"])

    model_config = ConfigDict(populate_by_name=True)