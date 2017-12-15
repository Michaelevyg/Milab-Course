var express = require('express');
var router = express.Router();
const fs = require('fs');

/* GET users listing. */
router.get('/', function(req, res, next) {
  fs.readFile(req.query.filename + ".txt",(err, content) => {
      if (err) {
          console.error(err);
          return;
      }
      res.send('The content of ' + "'" + req.query.filename + "' is '" + content + "'");
  });
});


module.exports = router;
