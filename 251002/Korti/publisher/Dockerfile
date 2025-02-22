# syntax=docker/dockerfile:1
ARG NODE_VERSION=22.9.0

FROM node:${NODE_VERSION}-alpine AS base
WORKDIR /usr/src

RUN npm install -g @nestjs/cli

FROM base AS deps
COPY package*.json tsconfig*.json ./
RUN npm install

FROM deps AS build
COPY . .
RUN npm run build

FROM base AS production
COPY --from=build /usr/src/dist ./dist
COPY --from=deps /usr/src/node_modules ./node_modules
COPY package*.json ./


ENV PORT=24110
EXPOSE 24110

USER node
CMD ["node", "dist/main.js"]