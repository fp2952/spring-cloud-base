FROM node:8.0.0
ADD . /app/
WORKDIR /app
RUN npm install
RUN npm rebuild node-sass --force
EXPOSE 8080
CMD ["npm", "start"]
