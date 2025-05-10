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
from app.cache import cache

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
        response = WriterResponseTo(**writer.model_dump())
        # Очищаем кеш при создании нового писателя
        cache.delete("writers:all")
        return response

    @staticmethod
    def get_by_id(id: int) -> WriterResponseTo:
        cache_key = f"writer:{id}"
        cached = cache.get(cache_key)
        if cached:
            return WriterResponseTo(**cached)
        
        writer = writer_repo.find_by_id(id)
        if not writer:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Writer not found")
        
        response = WriterResponseTo(**writer.model_dump())
        cache.set(cache_key, response.model_dump())
        return response

    @staticmethod
    def get_all() -> list[WriterResponseTo]:
        cache_key = "writers:all"
        cached = cache.get(cache_key)
        if cached:
            return [WriterResponseTo(**w) for w in cached]
        
        writers = [WriterResponseTo(**w.model_dump()) for w in writer_repo.find_all()]
        cache.set(cache_key, [w.model_dump() for w in writers])
        return writers

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
        response = WriterResponseTo(**updated.model_dump())
        
        # Обновляем кеш
        cache.delete(f"writer:{dto.id}")
        cache.delete("writers:all")
        return response

    @staticmethod
    def delete(id: int) -> None:
        if not writer_repo.delete(id):
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Writer not found")
        
        # Очищаем кеш
        cache.delete(f"writer:{id}")
        cache.delete("writers:all")

    @staticmethod
    def get_by_news_id(news_id: int) -> WriterResponseTo:
        cache_key = f"writer:news:{news_id}"
        cached = cache.get(cache_key)
        if cached:
            return WriterResponseTo(**cached)
        
        news = news_repo.find_by_id(news_id)
        if not news:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="News not found")
        
        writer = writer_repo.find_by_id(news.writer_id)
        if not writer:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Writer not found")
        
        response = WriterResponseTo(**writer.model_dump())
        cache.set(cache_key, response.model_dump())
        return response

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
        response = NewsResponseTo(**news.model_dump())
        
        # Очищаем кеш
        cache.delete("news:all")
        cache.delete(f"writer:news:{dto.writer_id}")
        return response

    @staticmethod
    def get_by_id(id: int) -> NewsResponseTo:
        cache_key = f"news:{id}"
        cached = cache.get(cache_key)
        if cached:
            return NewsResponseTo(**cached)
        
        news = news_repo.find_by_id(id)
        if not news:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="News not found")
        
        response = NewsResponseTo(**news.model_dump())
        cache.set(cache_key, response.model_dump())
        return response

    @staticmethod
    def get_all() -> list[NewsResponseTo]:
        cache_key = "news:all"
        cached = cache.get(cache_key)
        if cached:
            return [NewsResponseTo(**n) for n in cached]
        
        news_list = [NewsResponseTo(**n.model_dump()) for n in news_repo.find_all()]
        cache.set(cache_key, [n.model_dump() for n in news_list])
        return news_list

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
        
        response = NewsResponseTo(**updated.model_dump())
        
        # Обновляем кеш
        cache.delete(f"news:{id}")
        cache.delete("news:all")
        cache.delete(f"writer:news:{dto.writer_id}")
        return response

    @staticmethod
    def delete(id: int) -> None:
        news = news_repo.find_by_id(id)
        if not news:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="News not found")
        
        if not news_repo.delete(id):
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="News not found")
        
        # Очищаем кеш
        cache.delete(f"news:{id}")
        cache.delete("news:all")
        cache.delete(f"writer:news:{news.writer_id}")

    @staticmethod
    def search(**kwargs) -> NewsResponseTo:
        cache_key = f"news:search:{hash(frozenset(kwargs.items()))}"
        cached = cache.get(cache_key)
        if cached:
            return NewsResponseTo(**cached)
        
        for news in news_repo.find_all():
            match = True
            for key, value in kwargs.items():
                if value is not None and getattr(news, key, None) != value:
                    match = False
                    break
            if match:
                response = NewsResponseTo(**news.model_dump())
                cache.set(cache_key, response.model_dump())
                return response
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
        response = LabelResponseTo(**label.model_dump())
        
        # Очищаем кеш
        cache.delete("labels:all")
        return response

    @staticmethod
    def get_by_id(id: int) -> LabelResponseTo:
        cache_key = f"label:{id}"
        cached = cache.get(cache_key)
        if cached:
            return LabelResponseTo(**cached)
        
        label = label_repo.find_by_id(id)
        if not label:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Label not found")
        
        response = LabelResponseTo(**label.model_dump())
        cache.set(cache_key, response.model_dump())
        return response

    @staticmethod
    def get_all() -> list[LabelResponseTo]:
        cache_key = "labels:all"
        cached = cache.get(cache_key)
        if cached:
            return [LabelResponseTo(**l) for l in cached]
        
        labels = [LabelResponseTo(**l.model_dump()) for l in label_repo.find_all()]
        cache.set(cache_key, [l.model_dump() for l in labels])
        return labels

    @staticmethod
    def update(id: int, dto: LabelRequestTo) -> LabelResponseTo:
        label = Label(
            id=id,
            name=dto.name
        )
        updated = label_repo.update(id, label)
        if not updated:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Label not found")
        
        response = LabelResponseTo(**updated.model_dump())
        
        # Обновляем кеш
        cache.delete(f"label:{id}")
        cache.delete("labels:all")
        return response

    @staticmethod
    def delete(id: int) -> None:
        if not label_repo.delete(id):
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Label not found")
        
        # Очищаем кеш
        cache.delete(f"label:{id}")
        cache.delete("labels:all")

    @staticmethod
    def get_by_news_id(news_id: int) -> list[LabelResponseTo]:
        cache_key = f"labels:news:{news_id}"
        cached = cache.get(cache_key)
        if cached:
            return [LabelResponseTo(**l) for l in cached]
        
        labels = [l for l in label_repo.find_all() if getattr(l, 'news_id', None) == news_id]
        response = [LabelResponseTo(**l.model_dump()) for l in labels]
        cache.set(cache_key, [l.model_dump() for l in response])
        return response

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
        response = MessageResponseTo(**message.model_dump())
        
        # Очищаем кеш
        cache.delete(f"messages:news:{dto.news_id}")
        return response

    @staticmethod
    def get_by_id(id: int) -> MessageResponseTo:
        cache_key = f"message:{id}"
        cached = cache.get(cache_key)
        if cached:
            return MessageResponseTo(**cached)
        
        message = message_repo.find_by_id(id)
        if not message:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Message not found")
        
        response = MessageResponseTo(**message.model_dump())
        cache.set(cache_key, response.model_dump())
        return response

    @staticmethod
    def get_all() -> list[MessageResponseTo]:
        cache_key = "messages:all"
        cached = cache.get(cache_key)
        if cached:
            return [MessageResponseTo(**m) for m in cached]
        
        messages = [MessageResponseTo(**m.model_dump()) for m in message_repo.find_all()]
        cache.set(cache_key, [m.model_dump() for m in messages])
        return messages

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
        
        response = MessageResponseTo(**updated.model_dump())
        
        # Обновляем кеш
        cache.delete(f"message:{id}")
        cache.delete(f"messages:news:{dto.news_id}")
        return response

    @staticmethod
    def delete(id: int) -> None:
        message = message_repo.find_by_id(id)
        if not message:
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Message not found")
        
        if not message_repo.delete(id):
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Message not found")
        
        # Очищаем кеш
        cache.delete(f"message:{id}")
        cache.delete(f"messages:news:{message.news_id}")

    @staticmethod
    def get_by_news_id(news_id: int) -> list[MessageResponseTo]:
        cache_key = f"messages:news:{news_id}"
        cached = cache.get(cache_key)
        if cached:
            return [MessageResponseTo(**m) for m in cached]
        
        messages = [m for m in message_repo.find_all() if m.news_id == news_id]
        response = [MessageResponseTo(**m.model_dump()) for m in messages]
        cache.set(cache_key, [m.model_dump() for m in response])
        return response