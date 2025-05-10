from fastapi import FastAPI, HTTPException, Request
from fastapi.responses import JSONResponse
from app.routers import router as api_router
from app.schemas import WriterRequestTo

app = FastAPI(title="CRUD API for Writer, News, Label, Message")

@app.exception_handler(HTTPException)
async def custom_http_exception_handler(request: Request, exc: HTTPException):
    code = exc.status_code
    error_code = f"{code}01"  # например, 40401
    return JSONResponse(
        status_code=code,
        content={"errorMessage": exc.detail, "errorCode": error_code}
    )

@app.on_event("startup")
async def seed_data():
    from app.services import WriterService
    from app.db import AsyncSessionLocal
    async with AsyncSessionLocal() as session:
        try:
            await WriterService.get_by_id(session, 1)
        except HTTPException:
            await WriterService.create(
                session,
                WriterRequestTo(
                    login="sagatovmirsaid7@gmail.com",
                    password="Turkey2727",
                    firstname="Мирсаид",
                    lastname="Сагатов"
                )
            )

app.include_router(api_router)