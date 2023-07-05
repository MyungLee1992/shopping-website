FROM node:16.16.0 as node
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

FROM nginx:latest
COPY --from=node /app/dist/shoppingwebsiteapp /usr/share/nginx/html
EXPOSE 80
