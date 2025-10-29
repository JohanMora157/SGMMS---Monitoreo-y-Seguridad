# 🚓 SGMMS - Monitoreo y Seguridad
![CentroDeMonitoreo](https://github.com/user-attachments/assets/8653fff3-77a4-43ed-a675-4f2d5d766473)



## Componentes 💻

- Mapa de ciudad modelado como grafo 🗺
- Vehiculos automatas 🚗
- Algoritmos de arboles y grafos 🌲
- Escena de login, menu, centro de monitoreom historiales y estadisticas finales 
- Colisiones, sonidos y movimientos por todo el mapa 💥🔊
- Misiones de resolucion de incidentes desde el panel de Centro de Monitoreo 
- Sistema de puntuacion 🪙
- Historial de incidentes resueltos 📜
- Incidentes ocurriendo en tiempo real 📜

## IDE utilizado Y herramientas utilizadas 🧰

- Intellijidea
- Java 🍵
- JavaFX  🍵🔊
- SceneBuilder  📜
- JUnit  
- Tiled
- Itch.io (assets)
  
## Manual de Controles 🎮

- *Avanzar/Arriba* 🔼 
W

- *Atras/Abajo* 🔽
S

- *Derecha* ⏩
D

- *Izquierda*  ⏪ 
A

- *Pitar* 🔔
P

- *Radio* 📻
R

- *Resolver el incidente* 🖱
  Clic

## Mecanicas ℹ

- El jugador inicia en la esquina superior izquierda del mapa y se puede mover libremente, pero tiene colisones, asi que si colisiona con otro vehiculo "Morirá" y reapareceera en el mismo punto de aparicion, el juego cuenta con un minuto de duracion base lo cual es editable desde el menu, ademas el jugador cuenta con una puntacion la cual sube o baja con cada eleccion de resolver incidente. cada incidente puede ser resuelto por medio de los 3 botones en la esquina superior izquierda. 
  
- Botones para resolver incidentes de 3 tipos y sus valores en puntos (Correcto e Incorrecto)

- El numero de automatas generado es de 5 a 10 random 🎲 (3 Ppatrullas, 3 Bomberos y 3 Ambulancias)
  
- Cada 5 segundos se puede generar un incidente 🔔

🔥 Incendio       👍+ 3 puntos  ❌ -1 Puntos 

👮‍♂ Robo           👍+ 2 puntos  ❌ -2 Puntos

🚘 Choques        👍+ 4 puntos  ❌ -2 Puntos

**Si el jugador se choca con otro vehiculo pierde 10 puntos y viene una ambulancia 🚑 para ayudarle a reaparecer** 

*NOTAS:* 

-Los incidentes ocurren de forma aleatoria y no se pueden tener 2 incidentes en el mismo lugar al mismo tiempo, si el mapa se llena de incidentes no se generarán mas durante ese periodo de tiempo

- Ver Historial de incidentes resueltos (Opcion en el menu) 🗞

- Ver Historial de incidentes Sin resolver en tiempo real (Opcion en el menu) 📄

- Cuando hay un incidente y el jugador esta cerca no se puede mover hasta que lo resuelva 


#  Autores: 

Santiago Florez Solarte

Johan David Mora Guzmán

Juan David Fajardo
