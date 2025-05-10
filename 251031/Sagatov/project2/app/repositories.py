from typing import Type, TypeVar, List, Optional
from sqlalchemy.orm import Session
from fastapi import HTTPException, status
from app.models import Base, Writer, News, Label, Message

T = TypeVar('T', bound=Base)

class DatabaseRepository:
    def __init__(self, db: Session, model: Type[T]):
        self.db = db
        self.model = model
    
    def create(self, entity: T) -> T:
        self.db.add(entity)
        self.db.commit()
        self.db.refresh(entity)
        return entity
    
    def get_by_id(self, id: int) -> Optional[T]:
        return self.db.query(self.model).filter(self.model.id == id).first()
    
    def get_all(self, skip: int = 0, limit: int = 100) -> List[T]:
        return self.db.query(self.model).offset(skip).limit(limit).all()
    
    def update(self, id: int, entity_data: dict) -> Optional[T]:
        entity = self.get_by_id(id)
        if not entity:
            return None
        for key, value in entity_data.items():
            setattr(entity, key, value)
        self.db.commit()
        self.db.refresh(entity)
        return entity
    
    def delete(self, id: int) -> bool:
        entity = self.get_by_id(id)
        if not entity:
            return False
        self.db.delete(entity)
        self.db.commit()
        return True