# MiAppEvaluacionIOT
Proyecto de evaluación para Aplicaciones Móviles IoT (TI3042).

## Criterios de Seguridad (Rúbrica 2.1.3)

Este proyecto aplica las siguientes medidas básicas de seguridad y buenas prácticas solicitadas en la pauta:

### 1. Principio de Mínima Exposición (Sin permisos innecesarios)
La aplicación **no solicita permisos innecesarios** del sistema. El archivo `AndroidManifest.xml` no requiere permisos sensibles como `CAMERA`, `LOCATION`, `CONTACTS` o `BLUETOOTH`. Esto reduce la superficie de ataque y protege la privacidad del usuario, cumpliendo con el principio de mínima exposición.

### 2. Sin Datos Sensibles "Hardcodeados"
No se almacenan contraseñas, tokens de API ni ninguna otra información sensible directamente en el código fuente (archivos `.kt` o `.xml`). Todos los ingresos de datos (email, contraseña) son gestionados por el usuario en tiempo de ejecución y no son persistidos ni expuestos en el código.

### 3. Mensajes Claros y Validados
Las interacciones del usuario (Login, Registro, Recuperación de clave) son confirmadas mediante `AlertDialogs` con mensajes claros que informan el resultado de la acción simulada.
