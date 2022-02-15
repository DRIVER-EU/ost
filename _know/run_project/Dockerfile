FROM node:6.10-alpine
COPY ./web .
WORKDIR web
RUN npm install phantomjs-prebuilt
RUN npm install node-sass
RUN npm install
RUN npm run deploy:prod
