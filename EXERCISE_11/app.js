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

// Read song by name / artist / genre / album
app.get('/', function(req, res) {
    if(req.query.name) {
        db.collection('songs').findOne({name: req.query.name}, function(err, song) {
            if (err || !song) {
                res.send("Can't find song");
            } else {
                res.send(song);
            }
        });
    } else if(req.query.artist) {
        db.collection('songs').find({artist: req.query.artist}).toArray((err, songs) => {
            if (err || !songs[0]) {
                res.send("Can't find artist");
            } else {
                res.send(songs);
            }
        });
    } else if(req.query.genre) {
        db.collection('songs').find({genre: req.query.genre}).toArray((err, songs) => {
            if (err || !songs[0]) {
                res.send("Can't find genre");
            } else {
                res.send(songs);
            }
        });
    } else if(req.query.album) {
        db.collection('songs').find({album: req.query.album}).toArray((err, songs) => {
            if (err || !songs[0]) {
                res.send("Can't find album");
            } else {
                res.send(songs);
            }
        });
    } else {
        res.send("enter a valid url");
    }
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