# A turn-based 2D MOBA game

To execute the game (1 server and 2 players), you can run this command:

```
mvn exec:java -Dexec.mainClass="lol.server.LOL2D" -q& ; sleep 1s; mvn exec:java -Dexec.mainClass="lol.client.Client" -q& ; mvn exec:java -Dexec.mainClass="lol.client.Client" -q
```
