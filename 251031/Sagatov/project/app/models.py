from pydantic import BaseModel, Field, ConfigDict
from typing import Union
from datetime import datetime

# Внутренние модели (доменные объекты)
class User(BaseModel):
    id: int
    login: str = Field(..., max_length=64)
    password: str = Field(..., min_length=8, max_length=128)
    firstname: str = Field(..., max_length=64)
    lastname: str = Field(..., max_length=64)

class Topic(BaseModel):
    id: int
    user_id: int = Field(..., alias="userId")
    title: str = Field(..., max_length=64)
    content: str = Field(..., max_length=2048)
    created: datetime
    modified: datetime

class Mark(BaseModel):
    id: int
    name: str = Field(..., min_length=2, max_length=32)

class Message(BaseModel):
    id: int
    topic_id: int = Field(..., alias="topicId")
    content: str = Field(..., max_length=2048)

# DTO для запросов (без id)
class UserRequestTo(BaseModel):
    login: str = Field(..., max_length=64)
    password: str = Field(..., min_length=8, max_length=128)
    firstname: str = Field(..., max_length=64)
    lastname: str = Field(..., max_length=64)

class TopicRequestTo(BaseModel):
    user_id: int = Field(..., alias="userId")
    title: str = Field(..., max_length=64)
    content: str = Field(..., max_length=2048)
    model_config = ConfigDict(populate_by_name=True)

class MarkRequestTo(BaseModel):
    name: str = Field(..., min_length=2, max_length=32)

class MessageRequestTo(BaseModel):
    topic_id: int = Field(..., alias="topicId")
    content: str = Field(..., min_length=2, max_length=2048)
    model_config = ConfigDict(populate_by_name=True)

# DTO для ответов (с id)
class UserResponseTo(BaseModel):
    id: int
    login: str = Field(..., max_length=64)
    password: str = Field(..., min_length=8, max_length=128)
    firstname: str = Field(..., max_length=64)
    lastname: str = Field(..., max_length=64)

class TopicResponseTo(BaseModel):
    id: int
    user_id: int = Field(..., alias="userId")
    title: str = Field(..., max_length=64)
    content: str = Field(..., max_length=2048)
    created: datetime
    modified: datetime
    model_config = ConfigDict(populate_by_name=True)

class MarkResponseTo(BaseModel):
    id: int
    name: str = Field(..., min_length=2, max_length=32)

class MessageResponseTo(BaseModel):
    id: int
    topic_id: int = Field(..., alias="topicId")
    content: str = Field(..., min_length=2, max_length=2048)
    model_config = ConfigDict(populate_by_name=True)

# DTO для обновления Topic
class TopicUpdateTo(BaseModel):
    id: Union[str, int]
    title: str = Field(..., max_length=64)
    content: str = Field(..., max_length=2048)
    user_id: int = Field(..., alias="userId")
    model_config = ConfigDict(populate_by_name=True)

# DTO для обновления Message
class MessageUpdateTo(BaseModel):
    id: int
    content: str = Field(..., min_length=2, max_length=2048)
    topic_id: int = Field(..., alias="topicId")
    model_config = ConfigDict(populate_by_name=True)

# DTO для обновления Mark
class MarkUpdateTo(BaseModel):
    id: int
    name: str = Field(..., min_length=2, max_length=32)
    model_config = ConfigDict(populate_by_name=True)