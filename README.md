# OOP Brick Breaker

An Arkanoid-style brick breaker game written in Java as an object-oriented programming exercise. Control the paddle, bounce the balls into the blocks, and clear the board before you run out of balls.

## Gameplay

- Move the paddle with the **Left** and **Right** arrow keys.
- Balls bounce off the walls, blocks and paddle.
- Hitting a block removes it and increases your score.
- You start with **3 balls**. A ball that falls past the bottom edge is lost.
- The game ends when all blocks are cleared or all balls are lost.

## Project structure

```
oop-brick-breaker/
├── lib/
│   └── biuoop-1.4.jar        External graphics library
└── src/brickbreaker/
    ├── game/                 Entry point and game loop
    │   ├── Ass5Game.java     main()
    │   └── Game.java
    ├── geometry/             Point, Line, Rectangle, Velocity
    ├── collision/            Collidable, CollisionInfo, GameEnvironment
    ├── sprites/              Sprite, SpriteCollection, Ball, Block, Paddle, ScoreIndicator
    ├── listeners/            HitListener, HitNotifier, BlockRemover, BallRemover, ScoreTrackingListener
    └── util/                 Counter, Util
```

The hit-event classes in `listeners/` implement the observer pattern: blocks notify listeners when hit, and listeners remove blocks or balls and update the score.

## Requirements

- Java JDK 8 or newer
- The `biuoop` graphics library, already included in `lib/biuoop-1.4.jar`.

## Build and run

From the project root:

```bash
# compile
mkdir -p bin
javac -cp "lib/*" -d bin $(find src -name "*.java")

# run (macOS / Linux)
java -cp "bin:lib/*" brickbreaker.game.Ass5Game

# run (Windows)
java -cp "bin;lib/*" brickbreaker.game.Ass5Game
```