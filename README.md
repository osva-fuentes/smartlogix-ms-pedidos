# ms-pedidos

Microservicio que procesa pedidos en SmartLogix SaaS aplicando el patrón Facade. Coordina la comunicación con ms-inventario mediante RestTemplate para validar y enriquecer la respuesta con datos reales del producto.

## Patrones de diseño implementados

| Patrón | Ubicación | Propósito |
|---|---|---|
| Facade | `PedidoFacade` | Oculta la complejidad de coordinar la consulta a ms-inventario, el cálculo de envío y el armado de la respuesta final detrás de un único método |

## Comunicación entre microservicios

Este microservicio **no accede directamente a la tabla de inventario**. En su lugar, usa `RestTemplate` para consultar a `ms-inventario` por HTTP:

```
ms-pedidos → InventarioClient → RestTemplate → GET http://ms-inventario:8080/api/inventario/{id}
```

La respuesta JSON es convertida automáticamente por Jackson en un `ProductoResponseDTO` propio de `ms-pedidos`, que vive en su propio paquete `dto` aunque tenga nombre similar al de `ms-inventario` — cada microservicio define el contrato que necesita consumir.

## Tecnologías

- Java 21
- Spring Boot 3.5.14
- RestTemplate
- Lombok
- Docker

## DTO implementados

- `PedidoRequestDTO`: datos de entrada (productoId, tipoEnvio, nombreCliente).
- `ProductoResponseDTO`: contrato de lo que ms-inventario devuelve (copia local).
- `PedidoResponseDTO`: combina los datos del pedido con el producto real obtenido vía RestTemplate.

## Endpoints

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/pedidos/procesar` | Recibe JSON con los datos del pedido y devuelve el resumen combinado |
| GET | `/api/pedidos/health` | Verifica que el servicio está activo |

### Ejemplo de petición

```json
POST /api/pedidos/procesar
Content-Type: application/json

{
  "productoId": 1,
  "tipoEnvio": "estandar",
  "nombreCliente": "Ana Torres"
}
```

### Ejemplo de respuesta

```json
{
  "nombreCliente": "Ana Torres",
  "tipoEnvio": "estandar",
  "costoEnvio": "$2990",
  "resumenPedido": "Pedido procesado | Cliente: Ana Torres | Producto: Producto de Prueba (Stock: DISPONIBLE) | Envío estandar: $2990",
  "producto": {
    "id": 1,
    "nombre": "Producto de Prueba",
    "stock": 18,
    "precio": 15000.0,
    "estadoStock": "DISPONIBLE"
  }
}
```

## Variables de entorno

| Variable | Descripción | Valor por defecto |
|---|---|---|
| `INVENTARIO_SERVICE_URL` | URL base de ms-inventario | `http://localhost:8080` |

Dentro de Docker Compose se configura como `http://ms-inventario:8080` para usar el nombre del servicio en la red interna.

## Cómo ejecutar localmente

### Requisitos
- Java 21
- Maven 3.9+
- ms-inventario debe estar corriendo (local o en Docker)

### Con Maven

```bash
mvn clean package -DskipTests
java -jar target/ms-pedidos-0.0.1-SNAPSHOT.jar
```

El servicio queda disponible en `http://localhost:8082`.

### Con Docker

```bash
docker build -t ms-pedidos .
docker run -p 8082:8082 -e INVENTARIO_SERVICE_URL=http://ms-inventario:8080 ms-pedidos
```

## Cómo probar

```bash
curl -X POST http://localhost:8082/api/pedidos/procesar \
  -H "Content-Type: application/json" \
  -d '{"productoId":1,"tipoEnvio":"estandar","nombreCliente":"Ana Torres"}'
```

## Estructura del proyecto

```
src/main/java/com/proyectofullstack/pedidos/
├── Controller/   PedidoController
├── Service/      PedidoFacade
├── client/       InventarioClient
├── config/       RestTemplateConfig
├── dto/          PedidoRequestDTO, PedidoResponseDTO, ProductoResponseDTO
└── PedidosApplication.java
```
