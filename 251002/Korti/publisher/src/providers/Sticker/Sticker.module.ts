import { Module } from '@nestjs/common';
import { StickerController } from './Sticker.controller';
import { StickerService } from './Sticker.service';

@Module({
  imports: [],
  providers: [StickerService],
  controllers: [StickerController],
})
export class StickerModule {}
