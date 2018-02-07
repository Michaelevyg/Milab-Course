var express = require('express');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var app = express();
const MongoClient = require('mongodb').MongoClient;
const MONGO_URL = 'mongodb://MichaelDB:MichaelDB@ds125368.mlab.com:25368/milab-songs';
const ObjectId = require('mongodb').ObjectId;
var db;

app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieParser());

// Connecting to db
MongoClient.connect(MONGO_URL, (err, database) => {
    if (err) {
        return console.log(err)
    } else {
        console.log("Connected to db");
        db = database.db("milab-songs")
    }
});

// Create new song
app.post('/', function(req, res) {
    var name = req.body.name;
    var artist = req.body.artist;
    var genre = req.body.genre;
    var album = req.body.album;
    var song = {name: name, artist: artist, genre: genre, album:album};
    db.collection('songs').insert([song], function(err) {
        if (err) {
            res.send("can't add song " + err)
        } else {
            res.send("Added the song ");
        }
    });
});

// Update exiting song
app.put('/', function(req, res) {
    var id = req.body.id;
    var name = req.body.name;
    var artist = req.body.artist;
    var genre = req.body.genre;
    var album = req.body.album;
    var song = {name: name, artist: artist, genre: genre, album:album};
    db.collection('songs').updateOne({_id: ObjectId(id)}, {$set: song}, function (err) {
        if (err) {
            res.send("Can't update song with id: " + id)
        } else {
            res.send("Updated song");
        }
    });
});

// Read song by name / artist / genre / album
app.get('/', function(req, res) {
    if(req.query.name) {
        readByName(req.query.name, res);
    } else if(req.query.artist) {
        readByArtist(req.query.artist, res);
    } else if(req.query.genre) {
        readByGenre(req.query.genre, res);
    } else if(req.query.album) {
        readByAlbum(req.query.album, res);
    } else {
        res.send("enter a valid url");
    }
});

// Read song by name
function readByName(song, res) {
    db.collection('songs').findOne({name: song}, function(err, document) {
        if (err || !document) {
            res.send("Can't find song");
        } else {
            sendSongData(document, res);
            res.end();
        }
    });
}

// Read song by artist
function readByArtist(artist, res) {
    db.collection('songs').find({artist: artist}).toArray((err, songs) => {
        if (err || !songs[0]) {
            res.send("Can't find song");
        } else {
            songs.forEach(doc => sendSongData(doc, res));
            res.end();
        }
    });
}

// Read song by genre
function readByGenre(genre, res) {
    db.collection('songs').find({genre: genre}).toArray((err, songs) => {
        if (err || !songs[0]) {
            res.send("Can't find song");
        } else {
            songs.forEach(doc => sendSongData(doc, res));
            res.end();
        }
    });
}

// Read song by album
function readByAlbum(album, res) {
    db.collection('songs').find({album: album}).toArray((err, songs) => {
        if (err || !songs[0]) {
            res.send("Can't find song");
        } else {
            songs.forEach(doc => sendSongData(doc, res));
            res.end();
        }
    });
}

// Send song Data
function sendSongData(song, res) {
    res.write(`name: ${song.name}\n`);
    res.write(`artist: ${song.artist}\n`);
    res.write(`genre: ${song.genre}\n`);
    res.write(`album: ${song.album}\n`);
    res.write(`id: ${song._id}\n\n`);
}

// Delete song
app.delete('/' , function(req, res) {
    var songId = req.query.id;
    db.collection('songs').deleteOne({ _id: ObjectId(songId) }, function (err) {
        if (err) {
            res.send("Can't delete song")
        } else {
            res.send("Deleted song");
        }
    });
});

module.exports = app;