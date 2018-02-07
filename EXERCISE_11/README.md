1. How to run: npm start
2. Read by song name example: http://localhost:3000/?name=Dust%20In%20The%20Wind
3. Read by artist example: http://localhost:3000/?artist=Scorpions
4. Read by genre example: http://localhost:3000/?genre=Rock
5. Read by album example: http://localhost:3000/?album=Crazy%20World
6. Create song - use Postman with: type: Post, url: http://localhost:3000/, body: {"name": "song name", "artist": "artist", "genre": "genre", "album": "album"}
7. Updtae song - use Postman with: type: Put, url: http://localhost:3000/, body: {"id": "song id", "name": "song name", "artist": "artist", "genre": "genre", "album": "album"}
7. Delete song - use Postman with: type: Delete, url: http://localhost:3000/?id=<song id>