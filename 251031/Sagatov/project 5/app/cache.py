import json
from typing import Any, Optional
import redis
from app.config import settings

class RedisCache:
    def __init__(self):
        self.redis_client = redis.Redis(
            host=settings.REDIS_HOST,
            port=settings.REDIS_PORT,
            db=settings.REDIS_DB,
            password=settings.REDIS_PASSWORD,
            decode_responses=True
        )
    
    def get(self, key: str) -> Optional[Any]:
        """Получить значение из кеша"""
        value = self.redis_client.get(key)
        if value:
            return json.loads(value)
        return None
    
    def set(self, key: str, value: Any, expire: int = 3600) -> None:
        """Установить значение в кеш"""
        self.redis_client.setex(
            key,
            expire,
            json.dumps(value)
        )
    
    def delete(self, key: str) -> None:
        """Удалить значение из кеша"""
        self.redis_client.delete(key)
    
    def clear(self) -> None:
        """Очистить весь кеш"""
        self.redis_client.flushdb()

# Создаем глобальный экземпляр кеша
cache = RedisCache() 