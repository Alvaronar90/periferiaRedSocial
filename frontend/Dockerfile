# Fase de compilación: Usamos una imagen de Node para compilar la app React
FROM node:16-alpine as build

WORKDIR /app

# Copia los archivos de package y package-lock (o yarn.lock)
COPY package*.json ./

# Instala las dependencias
RUN npm install

# Copia el resto del código fuente
COPY . .

# Ejecuta la compilación para producción
RUN npm run build

# Fase final: Usamos nginx para servir la aplicación compilada
FROM nginx:alpine

# Copia la build al directorio que nginx utiliza para servir contenido
COPY --from=build /app/build /usr/share/nginx/html

# Expone el puerto 80
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
