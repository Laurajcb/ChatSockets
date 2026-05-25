---
 
## 📋 Descripción
 
El sistema permite que múltiples clientes se conecten a un servidor y se comuniquen entre sí. Los mensajes pueden enviarse de forma privada (a un usuario específico) o a todos los conectados. El servidor gestiona las conexiones, el registro de usuarios y la desconexión.
 
---

## 🏗️ Arquitectura

```
Cliente 1 ──────────────────────────────┐
                                        │
Cliente 2 ──────────── SERVIDOR ────────┤  (Gestiona conexiones y mensajes)
                                        │
Cliente 3 ──────────────────────────────┘
```

Cada cliente que se conecta recibe un **Thread exclusivo** (`ClienteHandler`) que gestiona toda su comunicación con el servidor.
 
---

## 📁 Estructura del Proyecto

```
ChatSockets/
└── src/
    ├── Servidor.java          # Servidor principal — escucha y acepta conexiones
    ├── ClienteHandler.java    # Thread que gestiona cada cliente conectado
    └── Cliente.java           # Programa cliente que corre cada usuario
```
 
---

## ⚙️ Requisitos

- Java JDK 11 o superior
- No requiere dependencias externas
---

## 🚀 Cómo correr el proyecto

### 1. Compilar
```bash
javac src/Servidor.java src/ClienteHandler.java src/Cliente.java -d out
```

### 2. Iniciar el Servidor
```bash
java -cp out Servidor
```
Deberías ver:
```
Servidor iniciado, esperando clientes...
```

### 3. Conectar Clientes (en terminales separadas)
```bash
java -cp out Cliente
```
Repite este paso en tantas terminales como usuarios quieras simular.
 
---

## 💬 Cómo usar el chat

| Acción | Cómo hacerlo |
|---|---|
| Conectarse | Ingresar nombre de usuario cuando se solicite |
| Mensaje a todos | Escribir el mensaje y presionar Enter |
| Mensaje privado | Escribir `nombreUsuario mensaje` (ej: `poli2 Hola!`) |
| Desconectarse | Escribir `chao` |
 
---

## 🔧 Configuración

El servidor corre por defecto en:
- **IP:** `127.0.0.1` (localhost)
- **Puerto:** `59420`
  Para cambiar el puerto, modificar la variable `puerto` en `Servidor.java`.

---

## 📌 Flujo del Sistema

```
1. Servidor arranca y escucha en el puerto 59420
2. Cliente se conecta e ingresa su nombre
3. Servidor registra al cliente y notifica a todos
4. Clientes intercambian mensajes (privados o grupales)
5. Cliente escribe "chao" → servidor lo desconecta y notifica a todos
```
 
---

## 👥 Integrantes del Grupo

- Laura Callejas
- _----
---

## 📚 Tecnologías Utilizadas

- Java — `java.net.Socket`, `java.net.ServerSocket`
- Multithreading — `Thread`, `Runnable`
- Streams — `BufferedReader`, `PrintWriter`
 