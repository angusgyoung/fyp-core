FROM node:12-alpine
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 6881/udp
EXPOSE 3000
CMD [ "node", "dht-node.js" ]