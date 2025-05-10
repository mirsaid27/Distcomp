from typing import Dict, Generic, TypeVar, List, Optional
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.future import select
from sqlalchemy.orm import selectinload
from app.models import Writer, News, Label, Message

T = TypeVar('T')



class WriterRepository:
    @staticmethod
    async def create(session: AsyncSession, writer: Writer):
        session.add(writer)
        await session.commit()
        await session.refresh(writer)
        return writer

    @staticmethod
    async def get_by_id(session: AsyncSession, id: int):
        result = await session.execute(select(Writer).where(Writer.id == id))
        return result.scalar_one_or_none()

    @staticmethod
    async def get_all(session: AsyncSession, skip=0, limit=10):
        result = await session.execute(select(Writer).offset(skip).limit(limit))
        return result.scalars().all()

    @staticmethod
    async def update(session: AsyncSession, id: int, data: dict):
        writer = await WriterRepository.get_by_id(session, id)
        if not writer:
            return None
        for key, value in data.items():
            setattr(writer, key, value)
        await session.commit()
        await session.refresh(writer)
        return writer

    @staticmethod
    async def delete(session: AsyncSession, id: int):
        writer = await WriterRepository.get_by_id(session, id)
        if not writer:
            return False
        await session.delete(writer)
        await session.commit()
        return True

class NewsRepository:
    @staticmethod
    async def create(session: AsyncSession, news: News):
        session.add(news)
        await session.commit()
        await session.refresh(news)
        return news

    @staticmethod
    async def get_by_id(session: AsyncSession, id: int):
        result = await session.execute(select(News).where(News.id == id))
        return result.scalar_one_or_none()

    @staticmethod
    async def get_all(session: AsyncSession, skip=0, limit=10):
        result = await session.execute(select(News).offset(skip).limit(limit))
        return result.scalars().all()

    @staticmethod
    async def update(session: AsyncSession, id: int, data: dict):
        news = await NewsRepository.get_by_id(session, id)
        if not news:
            return None
        for key, value in data.items():
            setattr(news, key, value)
        await session.commit()
        await session.refresh(news)
        return news

    @staticmethod
    async def delete(session: AsyncSession, id: int):
        news = await NewsRepository.get_by_id(session, id)
        if not news:
            return False
        await session.delete(news)
        await session.commit()
        return True

class LabelRepository:
    @staticmethod
    async def create(session: AsyncSession, label: Label):
        session.add(label)
        await session.commit()
        await session.refresh(label)
        return label

    @staticmethod
    async def get_by_id(session: AsyncSession, id: int):
        result = await session.execute(select(Label).where(Label.id == id))
        return result.scalar_one_or_none()

    @staticmethod
    async def get_all(session: AsyncSession, skip=0, limit=10):
        result = await session.execute(select(Label).offset(skip).limit(limit))
        return result.scalars().all()

    @staticmethod
    async def update(session: AsyncSession, id: int, data: dict):
        label = await LabelRepository.get_by_id(session, id)
        if not label:
            return None
        for key, value in data.items():
            setattr(label, key, value)
        await session.commit()
        await session.refresh(label)
        return label

    @staticmethod
    async def delete(session: AsyncSession, id: int):
        label = await LabelRepository.get_by_id(session, id)
        if not label:
            return False
        await session.delete(label)
        await session.commit()
        return True

class MessageRepository:
    @staticmethod
    async def create(session: AsyncSession, message: Message):
        session.add(message)
        await session.commit()
        await session.refresh(message)
        return message

    @staticmethod
    async def get_by_id(session: AsyncSession, id: int):
        result = await session.execute(select(Message).where(Message.id == id))
        return result.scalar_one_or_none()

    @staticmethod
    async def get_all(session: AsyncSession, skip=0, limit=10):
        result = await session.execute(select(Message).offset(skip).limit(limit))
        return result.scalars().all()

    @staticmethod
    async def update(session: AsyncSession, id: int, data: dict):
        message = await MessageRepository.get_by_id(session, id)
        if not message:
            return None
        for key, value in data.items():
            setattr(message, key, value)
        await session.commit()
        await session.refresh(message)
        return message

    @staticmethod
    async def delete(session: AsyncSession, id: int):
        message = await MessageRepository.get_by_id(session, id)
        if not message:
            return False
        await session.delete(message)
        await session.commit()
        return True

# Аналогично реализуй NewsRepository, LabelRepository, MessageRepository