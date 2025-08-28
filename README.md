# Connect4

A Java implementation of the classic Connect Four game with a Swing-based GUI and two computer opponents.  The easy opponent picks random columns while the hard opponent searches the game tree using a bitboard representation, alpha–beta pruning, a transposition table and iterative deepening for strong play.

## Features

* **Interactive GUI** &ndash; play against the computer by clicking the column buttons.  The interface highlights the current player and asks to start a new game when finished.
* **Two difficulty levels**:
  * **Easy** &ndash; `CPUEasy` chooses a random legal move.
  * **Hard** &ndash; `CPUPerfect` performs a minimax search with positional heuristics and win-depth scoring to find optimal moves.
* **Bitboard game engine** &ndash; `BitBoard` stores the board for fast win checks and move generation.  The search engine orders moves from the centre outward for quicker cut-offs.
* **Cross-platform Java** &ndash; runs anywhere a JVM and graphical environment are available.

## Building

This project uses only the JDK.  To compile all sources into the `out` directory:

```bash
javac -d out $(find src -name "*.java")
```

## Running

Run the game by launching the `App` class.  The program displays dialog boxes to choose player colour and difficulty, then opens the GUI:

```bash
java -cp out App
```

> **Note:** the GUI requires a graphical environment; running headless (e.g. in a CI container) will throw a `HeadlessException`.

## Project structure

```
src/
├── App.java                # entry point
├── CPUPlayers/             # computer opponents
│   ├── CPUEasy.java        # random-move CPU
│   └── CPUPerfect.java     # minimax AI with alpha-beta, transposition table, heuristics
├── GameGUI/                # Swing user interface
│   └── GUI.java
└── GameMechanics/
    ├── BitBoard.java       # bitboard representation and helpers
    ├── GameControl.java    # orchestrates game flow, user/CPU turns
    └── Grid.java           # traditional array-based board used by GUI
```

## Contributing

Issues and pull requests are welcome.  Please compile the sources and run the game to verify changes before submitting.
