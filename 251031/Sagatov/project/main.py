from fastapi import FastAPI, HTTPException, Request
from fastapi.responses import JSONResponse
from app.routers import router

app = FastAPI(title="CRUD API for Writer, News, Label, Message")

@app.exception_handler(HTTPException)
async def http_exception_handler(request: Request, exc: HTTPException):
    return JSONResponse(
        status_code=exc.status_code,
        content={
            "errorMessage": exc.detail,
            "errorCode": f"{exc.status_code:03d}01"
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