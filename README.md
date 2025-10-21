# 🧩 Sistema CAE

El **Sistema CAE** fue diseñado para organizar correctamente todos los documentos y solicitudes de los estudiantes (certificados, constancias, homologaciones, etc.), asegurando que los casos se atiendan en el orden justo en que llegan.  
Su principal fortaleza es la capacidad de **deshacer (Undo)** y **rehacer (Redo)** acciones como registrar notas o cambiar estados, garantizando un historial de trabajo **seguro, reversible y confiable**.  

---

## 💡 Decisiones de Diseño

### ⚙️ Patrón de Comando y Control de Acciones
El requisito más importante fue implementar el **mecanismo de deshacer/rehacer**.  
Para lograrlo, cada acción del sistema se maneja como un objeto independiente (`modelo.Accion`) que guarda toda la información necesaria para ejecutarse, revertirse o repetirse.  
El **UndoRedoManager** utiliza dos **pilas**:
- 🧩 **Pila Undo** → Guarda las acciones realizadas.  
- 🔁 **Pila Redo** → Guarda las acciones deshechas, listas para rehacer.  
Este doble historial permite navegar entre acciones sin comprometer la integridad del sistema.

### 🧱 Decisiones de Estructura de Datos
El sistema fue diseñado con estructuras que priorizan rapidez, orden y seguridad:
- **Cola de Tickets (QuequeCAE)** → Mantiene los casos pendientes en orden FIFO (“el primero en entrar es el primero en salir”).  
  Asegura atención justa y organizada.
- **Lista Enlazada Simple (ListaNotas)** → Registra notas en cada ticket de forma ágil, insertando nuevas observaciones al inicio sin reordenar toda la lista.
- **HashMap (Historial de Finalizados)** → Permite acceder de inmediato a los tickets completados por su ID, optimizando consultas posteriores.

---

## 🏷️ Catálogo de Estados

Cada ticket pasa por distintas etapas que reflejan su progreso:

| Fase | Estado | Descripción |
|:------|:---------|:-------------|
| 🕓 Inicial | **EN_COLA** | El ticket acaba de llegar y espera su turno. |
| 🧑‍💻 Atención | **EN_ATENCIÓN** | El operador lo ha tomado y puede registrar notas o cambiar estados. |
| ⚙️ Trabajo | **EN_PROCESO** | Se están verificando o completando formularios. |
| 📄 Espera | **PENDIENTE_DOCS** | El trámite está detenido a la espera de documentos del estudiante. |
| ✅ Cierre | **COMPLETADO** | El caso se finalizó correctamente y pasa al historial inalterable. |

> 🔒 Importante: una vez finalizado (COMPLETADO o PENDIENTE_DOCS), el ticket ya no puede modificarse.

---

## 🛡️ Casos Borde

El sistema maneja escenarios críticos para mantener su estabilidad y coherencia:

### 🚫 Gestión de Cola
- **Fila vacía:** si se intenta iniciar atención sin tickets, el sistema notifica que no hay casos pendientes.  
- **Atención duplicada:** se prohíbe atender más de un caso a la vez; se debe finalizar el actual primero.

### 🔄 Control de Historial (Undo/Redo)
- **Límite de Undo:** si se deshacen todas las acciones disponibles, el sistema indica que no hay más pasos atrás.  
- **Redo sin Undo previo:** se bloquea si no existe una acción previamente revertida.

### 🗒️ Validación de Datos
- **Eliminar nota inexistente:** al ingresar un ID inválido, el sistema alerta y cancela la acción.  
- **Ticket inexistente:** al consultar un ticket no registrado, se muestra un mensaje de advertencia.

---

## 🚀 Guía de Ejecución

1. Ir a la clase principal **`SistemaCAE`** y ejecutarla.  
2. Aparecerá el menú principal con las siguientes opciones:

---

#### 🧾 Opción 1: Recepción de Nuevo Caso
Permite registrar un nuevo trámite ingresando el nombre o descripción del estudiante.  
El ticket se encola automáticamente con estado **EN_COLA**.

#### 👀 Opción 2: Consultar Casos en Espera
Muestra en pantalla todos los tickets pendientes con su ID, nombre y estado actual.

#### ▶️ Opción 3: Iniciar Atención
Toma el primer ticket en la fila (el más antiguo) y lo pasa al estado **EN_ATENCIÓN**.  
A partir de este punto, se puede registrar notas o cambiar el estado del caso.

#### 🧠 Opción 4: Gestión Activa del Ticket
Despliega un submenú que permite realizar operaciones sobre el caso actual:
1. Registrar Nota → Añade observaciones o avances del caso.  
2. Eliminar Nota → Permite borrar notas mediante su ID.  
3. Cambiar Estado → Modifica el estado del ticket según su progreso.  
4. Deshacer (Undo) → Revierte la última acción realizada.  
5. Rehacer (Redo) → Restaura una acción previamente deshecha.  
6. Finalizar Caso → Cierra el caso y lo archiva en el historial.  
0. Volver al Menú Principal.

#### 📜 Opción 5: Consultar Historial
Permite buscar un ticket finalizado por su ID, mostrando su información completa y todas las notas registradas durante el proceso.

#### ❌ Opción 0: Salir
Finaliza la ejecución del programa de manera segura.

---

## ✒️ Autores

👨‍💻 **Equipo de Desarrollo - Sistema CAE**  
Proyecto desarrollado en el marco académico de la Universidad Nacional de Loja.  

- Anderson Coello  
- Steven Jumbo  
- Yandri Piscocama  
- Pedro Jiménez  
- Santiago Villamagua  

---

⌨️ con ❤️ por el equipo **CAE** — Universidad Nacional de Loja 🎓
