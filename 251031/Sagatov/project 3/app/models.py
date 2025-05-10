from sqlalchemy import Column, Integer, String, Text, ForeignKey, Table, DateTime
from sqlalchemy.orm import relationship, declarative_base
from datetime import datetime

Base = declarative_base()

news_label = Table(
    "tbl_news_label", Base.metadata,
    Column("news_id", Integer, ForeignKey("tbl_news.id")),
    Column("label_id", Integer, ForeignKey("tbl_label.id"))
)

class Writer(Base):
    __tablename__ = "tbl_writer"
    id = Column(Integer, primary_key=True, index=True)
    login = Column(String, unique=True, nullable=False)
    password = Column(String, nullable=False)
    firstname = Column(String, nullable=False)
    lastname = Column(String, nullable=False)
    news = relationship("News", back_populates="writer")

class News(Base):
    __tablename__ = "tbl_news"
    id = Column(Integer, primary_key=True, index=True)
    writer_id = Column(Integer, ForeignKey("tbl_writer.id"))
    title = Column(String, nullable=False)
    content = Column(Text, nullable=False)
    created = Column(DateTime, default=datetime.utcnow)
    modified = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow)
    writer = relationship("Writer", back_populates="news")
    labels = relationship("Label", secondary=news_label, back_populates="news")
    messages = relationship("Message", back_populates="news")

class Label(Base):
    __tablename__ = "tbl_label"
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, nullable=False)
    news = relationship("News", secondary=news_label, back_populates="labels")

class Message(Base):
    __tablename__ = "tbl_message"
    id = Column(Integer, primary_key=True, index=True)
    news_id = Column(Integer, ForeignKey("tbl_news.id"))
    content = Column(Text, nullable=False)
    news = relationship("News", back_populates="messages")