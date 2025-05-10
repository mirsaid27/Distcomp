from fastapi import FastAPI, HTTPException, Request
from fastapi.responses import JSONResponse
from app.database import engine
from app.models import Base
from app.routers import router
import uvicorn

# Создаем таблицы при запуске
Base.metadata.create_all(bind=engine)

app = FastAPI(
    title="News Platform API",
    description="API for managing writers, news, labels and messages",
    version="1.0.0",
    docs_url="/api/docs",
    redoc_url="/api/redoc"
)

@app.exception_handler(HTTPException)
async def http_exception_handler(request: Request, exc: HTTPException):
    return JSONResponse(
        status_code=exc.status_code,
        content={
            "error": True,
            "message": exc.detail,
            "code": f"{exc.status_code:03d}01"
        }
    )
@app.on_event("startup")
def seed_data():
    from app.models import WriterRequestTo
    from app.services import WriterService
    try:
        WriterService.get_by_id(1)
    except HTTPException:
        WriterService.create(
            WriterRequestTo(
                login="sagatovmirsaid7@gmail.com",
                password="Turkey2727",
                firstname="Мирсаид",
                lastname="Сагатов"
            )
        )


app.include_router(router)

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=24110)