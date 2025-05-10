from fastapi import HTTPException, status
from datetime import datetime
from app.models import (
    Writer, News, Label, Message,
    WriterRequestTo, WriterResponseTo,
    NewsRequestTo, NewsResponseTo,
    LabelRequestTo, LabelResponseTo,
    MessageRequestTo, MessageResponseTo
)
from app.repositories import InMemoryRepository

# Инициализация репозиториев
writer_repo = InMemoryRepository[Writer]()
news_repo = InMemoryRepository[News]()
label_repo = InMemoryRepository[Label]()
message_repo = InMemoryRepository[Message]()

# Сервис для Writer
class WriterService:
    @staticmethod
    def create(dto: WriterRequestTo) -> WriterResponseTo:
        writer = Writer(
            id=0,
            login=dto.login,
            password=dto.password,
            firstname=dto.firstname,
            lastname=dto.lastname
        )
        writer = writer_repo.create(writer)
        return WriterResponseTo(**writer.model_dump())

    @staticmethod
    def get_by_id(id: int) -> WriterResponseTo:
        writer = writer_repo.find_by_id(id)
        if not writer:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Writer not found")
        return WriterResponseTo(**writer.model_dump())

    @staticmethod
    def get_all() -> list[WriterResponseTo]:
        return [WriterResponseTo(**w.model_dump()) for w in writer_repo.find_all()]

    @staticmethod
    def update(dto: WriterResponseTo) -> WriterResponseTo:
        if not writer_repo.find_by_id(dto.id):
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Writer not found")
        writer = Writer(
            id=dto.id,
            login=dto.login,
            password=dto.password,
            firstname=dto.firstname,
            lastname=dto.lastname
        )
        updated = writer_repo.update(dto.id, writer)
        return WriterResponseTo(**updated.model_dump())

    @staticmethod
    def delete(id: int) -> None:
        if not writer_repo.delete(id):
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Writer not found")

    @staticmethod
    def get_by_news_id(news_id: int) -> WriterResponseTo:
        news = news_repo.find_by_id(news_id)
        if not news:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="News not found")
        writer = writer_repo.find_by_id(news.writer_id)
        if not writer:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Writer not found")
        return WriterResponseTo(**writer.model_dump())

# Сервис для News
class NewsService:
    @staticmethod
    def create(dto: NewsRequestTo) -> NewsResponseTo:
        news = News(
            id=0,
            writer_id=dto.writer_id,
            title=dto.title,
            content=dto.content,
            created=datetime.now(),
            modified=datetime.now()
        )
        news = news_repo.create(news)
        return NewsResponseTo(**news.model_dump())

    @staticmethod
    def get_by_id(id: int) -> NewsResponseTo:
        news = news_repo.find_by_id(id)
        if not news:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="News not found")
        return NewsResponseTo(**news.model_dump())

    @staticmethod
    def get_all() -> list[NewsResponseTo]:
        return [NewsResponseTo(**n.model_dump()) for n in news_repo.find_all()]

    @staticmethod
    def update(id: int, dto: NewsRequestTo) -> NewsResponseTo:
        news = News(
            id=id,
            writer_id=dto.writer_id,
            title=dto.title,
            content=dto.content,
            created=datetime.now(),
            modified=datetime.now()
        )
        updated = news_repo.update(id, news)
        if not updated:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="News not found")
        return NewsResponseTo(**updated.model_dump())

    @staticmethod
    def delete(id: int) -> None:
        if not news_repo.delete(id):
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="News not found")

    @staticmethod
    def search(**kwargs) -> NewsResponseTo:
        for news in news_repo.find_all():
            match = True
            for key, value in kwargs.items():
                if value is not None and getattr(news, key, None) != value:
                    match = False
                    break
            if match:
                return NewsResponseTo(**news.model_dump())
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="News not found")

# Сервис для Label
class LabelService:
    @staticmethod
    def create(dto: LabelRequestTo) -> LabelResponseTo:
        label = Label(
            id=0,
            name=dto.name
        )
        label = label_repo.create(label)
        return LabelResponseTo(**label.model_dump())

    @staticmethod
    def get_by_id(id: int) -> LabelResponseTo:
        label = label_repo.find_by_id(id)
        if not label:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Label not found")
        return LabelResponseTo(**label.model_dump())

    @staticmethod
    def get_all() -> list[LabelResponseTo]:
        return [LabelResponseTo(**l.model_dump()) for l in label_repo.find_all()]

    @staticmethod
    def update(id: int, dto: LabelRequestTo) -> LabelResponseTo:
        label = Label(
            id=id,
            name=dto.name
        )
        updated = label_repo.update(id, label)
        if not updated:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Label not found")
        return LabelResponseTo(**updated.model_dump())

    @staticmethod
    def delete(id: int) -> None:
        if not label_repo.delete(id):
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Label not found")

    @staticmethod
    def get_by_news_id(news_id: int) -> list[LabelResponseTo]:
        labels = [l for l in label_repo.find_all() if getattr(l, 'news_id', None) == news_id]
        return [LabelResponseTo(**l.model_dump()) for l in labels]

# Сервис для Message
class MessageService:
    @staticmethod
    def create(dto: MessageRequestTo) -> MessageResponseTo:
        message = Message(
            id=0,
            news_id=dto.news_id,
            content=dto.content
        )
        message = message_repo.create(message)
        return MessageResponseTo(**message.model_dump())

    @staticmethod
    def get_by_id(id: int) -> MessageResponseTo:
        message = message_repo.find_by_id(id)
        if not message:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Message not found")
        return MessageResponseTo(**message.model_dump())

    @staticmethod
    def get_all() -> list[MessageResponseTo]:
        return [MessageResponseTo(**m.model_dump()) for m in message_repo.find_all()]

    @staticmethod
    def update(id: int, dto: MessageRequestTo) -> MessageResponseTo:
        message = Message(
            id=id,
            news_id=dto.news_id,
            content=dto.content
        )
        updated = message_repo.update(id, message)
        if not updated:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Message not found")
        return MessageResponseTo(**updated.model_dump())

    @staticmethod
    def delete(id: int) -> None:
        if not message_repo.delete(id):
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Message not found")

    @staticmethod
    def get_by_news_id(news_id: int) -> list[MessageResponseTo]:
        messages = [m for m in message_repo.find_all() if m.news_id == news_id]
        return [MessageResponseTo(**m.model_dump()) for m in messages]