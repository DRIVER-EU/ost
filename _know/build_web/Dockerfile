FROM node:6.10-alpine
RUN mkdir build
COPY ./web .
WORKDIR web
RUN apk add --no-cache --virtual .gyp python
RUN npm install phantomjs-prebuilt
RUN npm install node-sass
RUN npm install
RUN npm run deploy:prod
RUN cp -r /web/dist /build/