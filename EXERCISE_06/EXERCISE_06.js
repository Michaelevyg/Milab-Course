const http = require('http');
const word = "milab";
var ans = "Word was: " + word + " ,new words are: " + word + " ";
var nextWord = word;
var i;
for (i = 1; i < word.length; i++) {
	nextWord = nextWord.substring(nextWord.length - 1, nextWord.length) + nextWord.substring(0, nextWord.length - 1);
    ans += nextWord + " "
	
}

let server = http.createServer(function(request, response) {
	response.writeHead(200);
	response.end(ans);
});
server.listen(8080);