# Portafolio API
### Servicio web que provee de informacion a cerca de mis aptitudes y conocimientos en las tecnologias de programacion.

## Peticiones HTTP Disponibles
## ________________________________________________
## UserProfile
### Crear Perfil de Usuario
POST -> `http://localhost:8080/api/user/save`
#### Parametros:
```
{
  name: String, 
  email: String, 
  bio: String, 
  thumbnail: MultipartFile
}
```
## ________________________________________________
### Buscar usuario por nombre
GET -> `http://localhost:8080/api/user/{name}`
#### Parametros:
`{ name: String }` _=> nombre del usuario a buscar_
## ________________________________________________
## Technology
### Registrar Tecnologia Usada
POST -> `http://localhost:8080/api/technology/save`
#### Parametros:
```
{
 name: String, 
 description: String, 
 thumbnail: MultipartFile,
 userName: String
}
```
## ________________________________________________
### Obtener Tecnologia Por Nombre
GET -> `http://localhost:8080/api/technology/{name}`
#### Parametros
`{ name: String }` _=> nombre de la tecnologia buscada_
## ________________________________________________
### Obtener Tecnologia Por Nombre de Usuario
GET -> `http://localhost:8080/api/technology/user/{name}`
#### Parametros
`{ name: String }` _=> nombre del usuario que maneja esa tecnologia_
## ________________________________________________
### Crear Nuevo Proyecto
POST -> `http://localhost:8080/api/project/save`
#### Parametros:
```
{
  title: String,
  description: String,
  technologyName: String,
  userName: String,
  thumbnails: List<MultipartFile>
}
```
## ________________________________________________
### Buscar Los Proyectos De Un Usuario
GET -> `http://localhost:8080/api/project/user/{name}`
#### Parametros:
`{ name: String }` _=> nombre del usuario_ 
## ________________________________________________
### Obtener la ruta completa de un archivo (Thumbnail)
GET -> `http://localhost:8080/api/thumbnail/{fileName}`
#### Parametros:
`{ name: String }` _=> nombre del archivo buscado_
